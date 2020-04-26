package com.cholewinskimichal.parser;

import org.jsoup.nodes.Document;

import java.util.List;

public interface WebsiteParser
{
    /**
     * @param document            to parse
     * @param searchedPhrases      list of phrases that have to be found on given document
     * @param currentLocationLink link to currently parsed document
     *
     * @return list of links that have to be visited
     */
    List<String> parseWebsite(Document document, List<String>  searchedPhrases, String currentLocationLink);


}
