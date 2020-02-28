# Converts given text file to lowercase letters
# USAGE: toLower.sh <text_file> <text_file_lowercase>

tr '[:upper:]' '[:lower:]' < $1 > $2
