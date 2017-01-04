Chris Luo(cdluo)
Stars README

Known Bugs
	With my testing, I did not find any bugs. However, I will admit that the GUI part of the project took me longer than expected, and I was not left with as much time to test as I would have liked. So I would not be surprised if I missed edge cases, and even now I can think of a few I didn't have time to test. For example, I didn't do performance/speed tests with peers or do any tests for extremely large trees.


Design Details Specific to my Code
	The main class is the KDTree, and everything else supports it. Dimen, Star, and their respective comparators are just for representing data and sorting it. The DistQueue and DQEntry were used for supporting the KNN search. I think it was overall a pretty straightforward design. I felt pretty "in-control" throughout development, so it worked pretty well for me.


Runtime/Space Optimizations
	I think my DistQueue class was an efficient way of searching KNN and only maintaining the closest neighbors. Sorting the array allowed me to do things like
find the current highest distance just by looking at the last array index. It also kept the array to a minimum size ot prevent using more space. 


How to Run Tests
	The 4 tests I provided in the src/test/java package are run automatically if one runs "mvn package." To test some of my other error checking, I provided "badInput" and "badHeader" CSV files to show that I checked for those as well during my CSV parsing.


How to Build/Run
	Nothing fancy here, just "mvn package" and then "./run [csv file] [--gui]


Design Qs
1. If I were to add a third command, then in my current implementation, I would have to make more conditionals for the String command in ln 170 of my Main class. Now that I think about it though, perhaps there was a better way to implement this functionality. I could have made a map between Strings and methods in the Main class and then checked command with this map to figure out what to run. This would keep the code more succint and organized, so if there were lots of commands, this would be smarter. Because there were only 2, I thought my current way would do fine when i was first coding it, but I guess you never know in the beginning huh?

2. If I were trying to use the KD Tree to find latitude and longitude points close to the Earth's surface, it would be hard to gauge distance without more paramters. 
Latitude and Longitude would make the KD Tree seem 2D, but in reality, the earth is curved, and the 2D distance formula wouldn't be totally accurate. Thus there wouldn't be an easy way to immediately perform an accurate radius or NN search.


CheckStyle Errors
	There are some package import errors in Main that I didn't know how to get rid of. I tried re-adding the packages, but then I got "unused package import" errors, so I wasn't sure how to go about it.

The rest of the remaining errors are all for magic numbers in my StarGenDummy. This
class was used to generate dummy CSV Star arrays, so the magic numbers were indeed that: arbitrary. So I had to have these errors.


