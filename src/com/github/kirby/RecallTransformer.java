package com.github.kirby;

import java.io.*;

public class RecallTransformer {

    private String inputFilePath;
    private String outputFilePath;


    public RecallTransformer(String propertyFile) throws IOException {

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
        int lineTrack = 0;
        int sampleIndex = 0;

        while((inputLine = inputReader.readLine()) != null){

            String[] fields = inputLine.split("\t");


            //Header Row.. Convert Sample to VisitId\tSequence Number
            if (lineTrack == 0){

                for(int i = 0; i < fields.length; i++){
                    if (fields[i].equalsIgnoreCase("SAMPLE")){
                        sampleIndex = i;
                        outputWriter.write("ParticipantID\tSequence_Num\t");
                    } else {
                        outputWriter.write(fields[i] + "\t");
                    }

                }
                outputWriter.newLine();


            //Body of TSV... Convert the sample field into two (0:ParticipantId, 1:Sequence_Num), otherwise output as normal
            } else {
                for(int i = 0; i < fields.length; i++){
                    if (i == sampleIndex){
                        String[] sampleSplit = fields[i].split("\\.");
                        outputWriter.write(String.format("%s\t%s\t", sampleSplit[0], sampleSplit[1]));

                    } else {
                        outputWriter.write(fields[i] + "\t");
                    }
                }
                outputWriter.newLine();
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
            RecallTransformer ft = new RecallTransformer(args[0]);
        } catch (IOException e){
            e.printStackTrace();
        }

    }
}
