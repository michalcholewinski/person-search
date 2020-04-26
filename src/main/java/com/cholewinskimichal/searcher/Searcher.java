package com.cholewinskimichal.searcher;

import java.io.IOException;
import java.util.List;

public interface Searcher
{
//    String ENTRY_POINT = "https://www.linkedin.com/feed/";
    String ENTRY_POINT = "http://demotywatory.pl/";

    /**
     * Searches for desired phrase
     *
     * @param searchedPhrase
     */
    void browseWeb(List<String> searchedPhrase) throws IOException;
}
