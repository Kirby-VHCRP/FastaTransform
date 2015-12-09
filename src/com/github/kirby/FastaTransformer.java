package com.github.kirby;

import java.io.*;

public class FastaTransformer {

    private String inputFilePath;
    private String outputFilePath;


    public FastaTransformer(String propertyFile) throws IOException {

        transformFile(propertyFile);

    }

    private void transformFile(String propertyFile) throws IOException {

        FileInputStream fis = new FileInputStream(new File(propertyFile));
        BufferedReader propReader = new BufferedReader(new InputStreamReader(fis));

        String line;
        while((line = propReader.readLine()) != null) {
            String[] propDetails = line.split("\\t");

            if (propDetails[0].equals("runDataFile")) {
                this.outputFilePath = propDetails[3];
            }

            if (propDetails[0].equals("runDataUploadedFile")){
                this.inputFilePath = propDetails[1];
            }

        }


        BufferedReader inputReader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(this.inputFilePath))));
        BufferedWriter outputWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(this.outputFilePath))));

        String inputLine;
        int lineTrack = 1;
        outputWriter.write("ParticipantID\tSequenceNum\tHCVSequence");
        outputWriter.newLine();

        while((inputLine = inputReader.readLine()) != null){

            if (lineTrack %2 == 0) {
                outputWriter.write(inputLine);
                outputWriter.newLine();
            } else {

                String[] cmps = inputLine.split("_");
                outputWriter.write(cmps[0].replace(">", "") + "\t");
                outputWriter.write(cmps[1] + "\t");
            }

            lineTrack++;

        }
        outputWriter.newLine();

        outputWriter.flush();
        outputWriter.close();
        inputReader.close();


    }

    public static void main(String[] args) {

        if (args.length != 1){
            throw new IllegalArgumentException("Invalid number of arguments");
        }

        try {
            FastaTransformer ft = new FastaTransformer(args[0]);
        } catch (IOException e){
            e.printStackTrace();
        }

    }
}
