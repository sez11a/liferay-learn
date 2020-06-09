import colorama
from colorama import Fore, Back, Style
import glob
import os
import re
import sys
from pprint import pprint
from pathlib import Path
# pip install inquirer
import inquirer

if __name__ == "__main__":

    if (len(sys.argv) < 3):
        pprint("Usage: migrate.py oldName newName\nIMPORTANT: Run this script in the folder containing the article to rename.")
        sys.exit()

    colorama.init() # colorama provides cross-platform color support for
                    # python's print functionality: init call is needed for
                    # windows

    oldName = sys.argv[1]
    newName = sys.argv[2]

    # Find article and dir names matching the pattern passed, then replace the
    # text passed in the first arg with the text from the second arg.

    # only look in these three file types
    types = ('*.md', '*.rst', '*.html')
    files_grabbed = []

    for filetype in types:
        files_grabbed.extend(glob.glob("**/" + filetype, recursive=True))

    matchingfiles = []

    for filename in files_grabbed:

        f = open(filename, "r")
        content = f.read()
        f.close()

        # Search for certain patterns that are good to replace. This should
        # cover all the text patterns we want to look at for replacement
        # without going too far,though this cannot be guaranteed.
        if (re.search(oldName + ".html",content) or
            re.search(oldName + ".md",content) or
            re.search("/" + oldName + "/", content) or
            re.search("/" + oldName + "`", content)):

            matchingfiles.append(filename)

    print(Back.LIGHTCYAN_EX + Fore.BLACK + "FILES WITH MATCHES TO THE OLD "
          "STRING:" + Style.RESET_ALL)

    choices = [
        inquirer.Checkbox(
            'files',
            message="The selected paths will undergo a search and replace. "
                "De-select a path with the spacebar",
            choices=matchingfiles,
            default=matchingfiles),
    ]

    filtermatches = inquirer.prompt(choices).values()

    matcheslist = list(filtermatches)

    warnhtmllines = []
    # Now we have the final list of files that contain the string and that the
    # user confirms they want to operate on, so here we do the replacement of
    # known good patterns for replacement.
    for each in matcheslist[0]:

        filepath = Path(each)
        f = open(filepath, "r")
        origlines = f.readlines()
        f.close()

        newlines = []

        lastline = ""

        for origline in origlines:
            if (re.search('/'+oldName+'.md',origline) or
                re.search('/'+oldName+'/',origline) or
                re.search('/'+oldName+'.html', origline) or
                re.search('/'+oldName+'`',origline)):

                newline = origline.replace(oldName,newName)

                newlines.extend(newline)

                re.search(newName,newline)

                print(Fore.LIGHTBLUE_EX + f.name + Fore.RESET + Fore.LIGHTYELLOW_EX + "\n" + newline)

                if each.endswith("landing.html"):
                    combo = Fore.BLUE + each + "\n" + Fore.YELLOW + lastline + newline
                    warnhtmllines.append(combo)

                lastline = newline


            else:
                newlines.extend(origline)
                lastline = origline

        f = open(filepath, "w")
        f.writelines(newlines)
        f.close()

    # for a file rename we only want to match anything/my-string.md and
    # anything/my-string/
    matchingfilepatterns = glob.glob("**/"+oldName+".md", recursive=True)
    matchingfilepatterns.extend(glob.glob("**/"+oldName+"/", recursive=True))

    print(Back.LIGHTCYAN_EX + Fore.BLACK + "FILES/FOLDERS TO RENAME" +
          Style.RESET_ALL)

    choices = [
        inquirer.Checkbox('filepatterns',
        message="The selected paths will be renamed. De-select a path "
            "with the spacebar",
        choices=matchingfilepatterns,
        default=matchingfilepatterns),
    ]

    chosenfilepatterns = inquirer.prompt(choices).values()

    renamelist = list(chosenfilepatterns)

    # now we have the final list of files to rename
    for renamefile in renamelist[0]:

        newfilename = re.sub(oldName, newName, renamefile, 1, 0)

        os.rename(renamefile, newfilename)

        print(Fore.BLUE + renamefile + "\n" + Fore.WHITE + " was renamed to " +
              "\n" + Fore. BLUE + newfilename)

        if newfilename.endswith('.md'):

            f = open(newfilename, "r")
            titleline = f.readline()
            f.close()

            print(Fore.YELLOW + "<<<Make sure the article's title "
                  "corresponds to the new file name>>>")
            print(Fore.YELLOW + titleline + "------------")

        else:

            print(Fore.YELLOW + "------------")

    print(Fore.WHITE + "For each " + Fore.BLUE + "landing.html " +
          Fore.WHITE + "file that was modified,")
    print(Fore.YELLOW + "<<<Make sure the 'name'"
          " attribute reflects the new content from the 'url' attribute>>> \n")
    for eachline in warnhtmllines:
        print(eachline)
        print(Fore.YELLOW + "------------")
