package com.cholewinskimichal.parser;

import com.cholewinskimichal.accumulator.ResultsAccumulator;
import com.cholewinskimichal.accumulator.ResultsAccumulatorImpl;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class WebsiteParserImpl implements WebsiteParser
{
    ResultsAccumulator accumulator = new ResultsAccumulatorImpl();

    public List<String> parseWebsite(Document document, List<String> searchedPhrases, String currentLocationLink)
    {
        if (document != null && document.body() != null)
        {
            String body = document.body().text();
            searchInText(searchedPhrases, body, currentLocationLink);
        }

        if (document != null && document.head() != null)
        {
            String head = document.head().text();
            searchInText(searchedPhrases, head, currentLocationLink);
        }

        return getLinks(document);
    }

    private void searchInText(List<String> searchedPhrases, String text, String currentLocationLink)
    {
        searchedPhrases.forEach(searchedPhrase ->
        {
            if (text.contains(searchedPhrase))
            {
                accumulator.addResult(currentLocationLink, searchedPhrase);
            }
        });
    }

    private List<String> getLinks(Document document)
    {
        if(document!=null)
        {
            Elements links = document.select("a[href]");
            return links.stream().map(link -> link.attr("abs:href")).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
}
