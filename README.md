Topic Finder (for wiki-tics in java) is a new feature/scrapper that is run hourly to find new topics of 
interest for http://wiki-tics.info.

This project uses the NYT NewWire API to get articles from the last couple hours.  The title and abstract 
from these articles are then submitted to ALCHEMYAPI to extract the keywords.  These key words are then checked
against the permanent topics in wiki-tics to check for duplicates.  The non-duplicates are then added to the MySQL
database to be called by wiki-tics in the future.

TopicFinder.java contains main().
