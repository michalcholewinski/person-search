package com.cholewinskimichal.searcher;

import com.cholewinskimichal.emails.EmailSender;
import com.cholewinskimichal.parser.WebsiteParser;
import com.cholewinskimichal.parser.WebsiteParserImpl;
import org.joda.time.DateTime;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SearcherImpl implements Searcher
{
    public static final int FIRST_VISIT = 1;
    public static final int NOT_VISITED = 0;
    public static final int SENT_RAPORT_AFTER_VISITED_PAGE_COUNT = 30000;
    WebsiteParser websiteParser = new WebsiteParserImpl();
    Map<String, Integer> websites = new HashMap<>();
    private int visitedPageCount = 0;

    public void browseWeb(List<String> searchedPhrase)
    {
        websites.put(ENTRY_POINT, NOT_VISITED);
        websites.put("https://stackoverflow.com/", NOT_VISITED);
        websites.put("https://4programmers.net/", NOT_VISITED);
        websites.put("https://oferia.pl/", NOT_VISITED);

        while (true)
        {
            List<String> linksToBeVisited = new ArrayList<>();
            Set<Map.Entry<String, Integer>> entries = websites.entrySet();
            for (Map.Entry<String, Integer> entry : entries)
            {
                if (entry.getValue() == NOT_VISITED)
                {
                    try
                    {
                        linksToBeVisited.addAll(visitPage(searchedPhrase, entry.getKey()));
                        visitedPageCount++;
                        if (visitedPageCount % SENT_RAPORT_AFTER_VISITED_PAGE_COUNT == 0)
                        {
                            EmailSender.sendEmailWithText("I visited for you " + visitedPageCount + " pages.\nI'm on page: " + entry.getKey() + "\nDate: " + DateTime.now(), "VISITED PAGE RAPORT!!!");
                        }
                    }
                    catch (IOException e)
                    {
                        System.out.println(e);
                        markWebsiteAsVisited(entry.getKey());
                    }
                }
            }
            populateWebsitesWithNewLinks(linksToBeVisited);
            if (websites.size() > 3000)
            {
                cleanVisitedWebsites();
            }
            System.out.println(websites.size());


        }

    }

    private void cleanVisitedWebsites()
    {
        System.out.println("Sprzątam");
        for (Iterator<Map.Entry<String, Integer>> it = websites.entrySet().iterator(); it.hasNext(); )
        {
            Map.Entry<String, Integer> entry = it.next();
            if (entry.getValue() != NOT_VISITED)
            {
                it.remove();
            }
        }
    }

    private List<String> visitPage(List<String> searchedPhrase, String url) throws IOException
    {
        //        System.out.println("Odwiedzam: " + url);
        Document doc = Jsoup.connect(url).get();
        markWebsiteAsVisited(url);
        return websiteParser.parseWebsite(doc, searchedPhrase, url);

    }

    private void populateWebsitesWithNewLinks(List<String> links)
    {
        links.forEach(link ->
        {
            if (websites.get(link) == null)
            {
                if (link != null && link.length() > 0)
                {
                    websites.put(link, NOT_VISITED);
                }
            }
        });
    }

    private void markWebsiteAsVisited(String url)
    {
        Integer visitCounter = websites.get(url);
        if (visitCounter == null || visitCounter == 0)
        {
            websites.put(url, FIRST_VISIT);
        }
        else
        {
            visitCounter++;
        }
    }
}
