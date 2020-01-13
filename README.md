# probeDesigner by using MSA sequences #

## Description ##
This program reads a MSA file to create probes and gives a summary of the given MSA file. The sequences are then
processed in order to calculate a few information. These information will be shown when the user provides the 
arguments to view the summary. The sequences which were processed are then used in addition with a HashMap containing 
IuPAC codes to generate the consensusSequences. Once the consensusSequences are generated, it will be used to calculate 
other information for the summary. To make probes, the consensusSequences is used. Aside of making probes, according to 
the user specifications, some other information are calculated like:
 
### Summary information ###
 
 * Filename
 * Download date
 * Length of the MSA file
 * Amount of unChanged residues
 * Amount of a few residues (R,N,W,Y)
 
## Designer information ##
 * Start location of the probes
 * Length of the probes
 * The probe sequences


## Usage ##
`java -jar Probdesigner-1.0.jar -f <MSA file> -s<summary> -d<designer> -n<minimsize> 12 -m<maximumsize> 25 -a<amount> 5
`

## Commandline information ##
* -f <MSAFILE> an MSA file is required
* -h <HELP> When no arguments are given, this message pops up with instructions
* -s <SUMMARY> when user would like to get the summary(is not required to get the probes)
* -d <DESIGNER> when user would like to view the probes(is not required to view the summary)
* -n <MINIMUM SIZE> User can specify a minimum size for the probes 
* -m <MAXIMUM SIZE> User can specify a maximum size for the probes 
* -a <MAXIMUM AMOUNT> User can specify a maximum amount of probes from the probes. 
