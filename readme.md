MCBans Server Plugin
====================

MCBans is a global ban system for the game Minecraft.
More information can be found on the [MCBans website](http://www.mcbans.com/) or [wiki](http://wiki.mcbans.com/).

Commands
--------

Below is a list of commands supported by MCBans 4. These are due to change before release.

* /lookup <playername>
* /kick <playername> [reason]
* /ban <playername> [g/reason] [reason]
* /unban <playername>
* /tempban <playername> <duration> <m[inutes]|h[ours]|d[ays]> [reason]

Coding Standards
----------------

* If in doubt, use the Sun/Oracle coding standards
* Tab/spacing to be decided
* Naming:
    * Packages should be lowercase
    * Class names should use UpperCamelCase (e.g. BanCommandHandler)
    * Methods should use lowerCamelCase and start with a verb (e.g. getVar(), saveConfig())
	* Fields should use lowerCamelCase and be descriptive of their use (e.g. callbackInterval, isOnline)
	* Constants should be UPPERCASE with words separated by underscores (e.g. DEFAULT_WIDTH)
* Proper javadoc should be added to all classes and complex methods
* No 80 column limits