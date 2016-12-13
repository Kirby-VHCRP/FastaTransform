# FastaTransform

This is a module that can be used in a LabKey Assay to transform the data that is loaded. This is specifically built to transform FASTA files into the desired tabular format.

## Usage

Set up the Assay in LabKey (General).
Edit the Assay design
Specify the transformation script as: `C:\Program Files\LabKey Server\files\VHKEY3\@files\FastaTranformation.jar`

![assay-props.png](doc/img/assay-props.png)

Specify the fields under [assay name] Data fields as:

* ParticipantID
* VisitID
* HCVSequence

The output should look like:

![assay-fields.png](doc/img/assay-fields.png)

At the top of the assay runs, click the "Import Data" button.

You will want to specify an `Assay Id` and the data to transform:

![run-props.png](doc/img/run-props.png)

Copy to the relevant study
