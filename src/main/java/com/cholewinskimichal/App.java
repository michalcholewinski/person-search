package com.cholewinskimichal;

import com.cholewinskimichal.emails.EmailSender;
import com.cholewinskimichal.searcher.Searcher;
import com.cholewinskimichal.searcher.SearcherImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class App 
{
    public static void main( String[] args ) throws IOException
    {
        Searcher searcher= new SearcherImpl();
        List<String> searchedPhrases = new ArrayList<>();
        searchedPhrases.add("Michał Cholewiński");
        searchedPhrases.add("Michal Cholewinski");
        searchedPhrases.add("Cholewinski Michal");
        searchedPhrases.add("Cholewiński Michał");
        searchedPhrases.add("cholewinskimichal@gmail.com");


        StringBuilder sb = new StringBuilder();
        searchedPhrases.forEach(phrase->{
            sb.append(phrase).append("\n");
        });
        EmailSender.sendEmailWithText(sb.toString(),"STARTED TO SEARCH FOLLOWING PHRASES!!!");

        searcher.browseWeb(searchedPhrases);
    }
}
