package com.cholewinskimichal.accumulator;

/**
 * Accumulates all results
 */
public interface ResultsAccumulator
{
    /**
     * Adds link with page where searched phrase had been found
     * @param link
     * @param searchedPhrase
     */
    void addResult(String link, String searchedPhrase);


}
