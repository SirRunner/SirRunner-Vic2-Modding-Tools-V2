# SirRunner-Vic2-Modding-Tools-V2
I got sick of attempting to figure out my spaghetti code in my other repo, so starting it fresh in Java

## Province History File Generator
 - Loads in a csv file (TTA specific), the definitions file, and regions file
 - Compares the province names between the csv file and definitions file to ensure that they are the same
 - Ensures that every land province has a region, and alerts if a province is set to be in multiple regions
 - Writes the localisation for both provinces and regions to a specified output
 - Write the province history file to a specified folder

## Renaming Decision Generator
 - Loads in 2 csv files (TTA specific) and the regions file
 - Compares the region codes to ensure that any regions defined in the csv file exist in the game
 - Splits up the renamings for region upon culture group
 - Finds the optimal way to go the renamings
   - Combines any culture groups that have the exact same names for regions and provinces into one section of the decision to help reduce lag from the decisions
 - Writes the localization to an output file
 - Writes the starting variable effects (to be put in the utility tag's starting decision)