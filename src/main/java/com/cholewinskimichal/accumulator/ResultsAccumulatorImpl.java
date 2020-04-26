package com.cholewinskimichal.accumulator;

import com.cholewinskimichal.emails.EmailSender;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ResultsAccumulatorImpl implements ResultsAccumulator
{
    private static final String RESULTS_FILE = "foundresults.txt";

    public ResultsAccumulatorImpl()
    {

    }

    @Override
    public void addResult(String link, String searchedPhrase)
    {
        File file = new File(RESULTS_FILE);
        FileWriter fileWriter = null;
        BufferedWriter bw = null;
        try
        {
            String mention = "Znaleziono wzmiankę o \"" + searchedPhrase + "\" na: " + link;
            EmailSender.sendEmailWithText(mention, "NEW MENTION FOUND!!!");
            if (!file.exists())
            {
                file.createNewFile();
            }
            fileWriter = new FileWriter(file.getAbsoluteFile(), true);
            bw = new BufferedWriter(fileWriter);
            bw.write(mention);
        }
        catch (IOException e)
        {
            System.out.println(e);
        }
        finally
        {
            try
            {

                if (bw != null)
                {
                    bw.close();
                }

                if (fileWriter != null)
                {
                    fileWriter.close();
                }

            }
            catch (IOException ex)
            {

                System.out.println("Problem z zamknięciem plików:\n+"+ex);

            }
        }
    }

}
