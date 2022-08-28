# SirRunner-Vic2-Modding-Tools-V2
I got sick of attempting to figure out my spaghetti code in my other repo, so starting it fresh in Java

## Province History File Generator
 - Loads in a csv file (TTA specific), the definitions file, and regions file
 - Compares the province names between the csv file and definitions file to ensure that they are the same
 - Ensures that every land province has a region, and alerts if a province is set to be in multiple regions
 - Writes the localisation for both provinces and regions to a specified output
 - Write the province history file to a specified folder
