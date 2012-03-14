package in.mDev.MiracleM4n.mChatSuite.commands;

import in.mDev.MiracleM4n.mChatSuite.api.InfoType;
import in.mDev.MiracleM4n.mChatSuite.mChatSuite;
import in.mDev.MiracleM4n.mChatSuite.util.Messanger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MChatCommand implements CommandExecutor {
    mChatSuite plugin;

    public MChatCommand(mChatSuite plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = null;

        if (sender instanceof Player)
            player = (Player) sender;

        String cmd = command.getName();

        if (args.length == 0)
            return false;

        if (cmd.equalsIgnoreCase("mchat")) {
            if (args[0].equalsIgnoreCase("version")) {
                if (sender instanceof Player) {
                    if (!plugin.getAPI().checkPermissions(player.getName(), player.getWorld().getName(), "mchat.version")) {
                        sender.sendMessage(Messanger.format("You are not to view the version of mChatSuite."));
                        return true;
                    }

                }

                String cVersion = "&6MineCraft Version: &2" + plugin.pdfFile.getVersion();

                cVersion = cVersion.replaceFirst("-", "^*^&6Jenkins Build&5#&5: &2");
                cVersion = cVersion.replaceFirst("-", "^*^&6Release Version: &2");
                cVersion = cVersion.replaceFirst("_", "^*^&6Fix&5#&5: &2");

                String[] vArray = cVersion.split("\\^\\*\\^");

                sender.sendMessage(Messanger.format("&6Full Version: &1" + plugin.pdfFile.getVersion()));

                for (String string : vArray)
                    sender.sendMessage(Messanger.format(string));

                return true;
            } else if (args[0].equalsIgnoreCase("reload")
                    || args[0].equalsIgnoreCase("r")) {
                if (args.length > 1)
                    if (args[1].equalsIgnoreCase("config")
                            || args[1].equalsIgnoreCase("co")) {
                        if (sender instanceof Player)
                            if (!plugin.getAPI().checkPermissions(player.getName(), player.getWorld().getName(), "mchat.reload.config")) {
                                sender.sendMessage(Messanger.format("You are not allowed to reload mChat."));
                                return true;
                            }

                        plugin.getMainConfig().reload();
                        plugin.getMainConfig().load();
                        sender.sendMessage(Messanger.format("Config Reloaded."));
                        return true;
                    } else if (args[1].equalsIgnoreCase("info")
                            || args[1].equalsIgnoreCase("i")) {
                        if (sender instanceof Player)
                            if (!plugin.getAPI().checkPermissions(player.getName(), player.getWorld().getName(), "mchat.reload.info")) {
                                sender.sendMessage(Messanger.format("You are not allowed to reload mChat."));
                                return true;
                            }

                        plugin.getInfoConfig().reload();
                        plugin.getInfoConfig().load();
                        sender.sendMessage(Messanger.format("Info Reloaded."));
                        return true;
                    } else if (args[1].equalsIgnoreCase("censor")
                            || args[1].equalsIgnoreCase("ce")) {
                        if (sender instanceof Player)
                            if (!plugin.getAPI().checkPermissions(player.getName(), player.getWorld().getName(), "mchat.reload.censor")) {
                                sender.sendMessage(Messanger.format("You are not allowed to reload mChat."));
                                return true;
                            }

                        plugin.getCensorConfig().reload();
                        plugin.getCensorConfig().load();
                        sender.sendMessage(Messanger.format("Censor Reloaded."));
                        return true;
                    } else if (args[1].equalsIgnoreCase("locale")
                            || args[1].equalsIgnoreCase("l")) {
                        if (sender instanceof Player)
                            if (!plugin.getAPI().checkPermissions(player.getName(), player.getWorld().getName(), "mchat.reload.locale")) {
                                sender.sendMessage(Messanger.format("You are not allowed to reload mChat."));
                                return true;
                            }

                        plugin.getLocale().reload();
                        sender.sendMessage(Messanger.format("Censor Reloaded."));
                        return true;
                    } else if (args[1].equalsIgnoreCase("all")
                            || args[1].equalsIgnoreCase("a")) {
                        if (sender instanceof Player)
                            if (!plugin.getAPI().checkPermissions(player.getName(), player.getWorld().getName(), "mchat.reload.all")) {
                                sender.sendMessage(Messanger.format("You are not allowed to reload mChat."));
                                return true;
                            }

                        plugin.reloadConfigs();
                        plugin.setupConfigs();
                        sender.sendMessage(Messanger.format("All Config's Reloaded."));
                        return true;
                    }
            } else if (args[0].equalsIgnoreCase("u")
                    || args[0].equalsIgnoreCase("user")) {
                if (args.length == 1) {
                    sender.sendMessage(Messanger.format("Use '/mchat user add/set/remove' for user help."));
                    return true;
                } else if (args[1].equalsIgnoreCase("a")
                        || args[1].equalsIgnoreCase("add")) {
                    if (args.length == 2) {
                        sender.sendMessage(Messanger.format("Usage for '/mchat user add':"));
                        sender.sendMessage("- /mchat user add player Player DefaultGroup");
                        sender.sendMessage("- /mchat user add ivar Player InfoVariable InfoValue");
                        sender.sendMessage("- /mchat user add world Player World");
                        sender.sendMessage("- /mchat user add wvar Player World InfoVariable InfoValue");
                        return true;
                    } else if (args[2].equalsIgnoreCase("p")
                            || args[2].equalsIgnoreCase("player")) {
                        if (sender instanceof Player)
                            if (!plugin.getAPI().checkPermissions(player.getName(), player.getWorld().getName(), "mchat.user.add.player")) {
                                sender.sendMessage(Messanger.format("You don't have Permission to do that."));
                                return true;
                            }
                        try {
                            plugin.getInfoWriter().addBase(args[3], args[4]);
                            sender.sendMessage(Messanger.format("Info Addition Successful."));
                            return true;
                        } catch (ArrayIndexOutOfBoundsException er) {
                            sender.sendMessage(Messanger.format("/mchat user add player Player DefaultGroup"));
                            return true;
                        }
                    } else if (args[2].equalsIgnoreCase("iVar")
                            || args[2].equalsIgnoreCase("infoVariable")) {
                        if (sender instanceof Player)
                            if (!plugin.getAPI().checkPermissions(player.getName(), player.getWorld().getName(), "mchat.user.add.ivar")) {
                                sender.sendMessage(Messanger.format("You don't have Permission to do that."));
                                return true;
                            }
                        try {
                            plugin.getInfoWriter().setInfoVar(args[3], InfoType.USER, args[4], stringArgs(args, 5));
                            sender.sendMessage(Messanger.format("Info Addition Successful."));
                            return true;
                        } catch (ArrayIndexOutOfBoundsException er) {
                            sender.sendMessage(Messanger.format("/mchat user add ivar Player InfoVariable InfoValue"));
                            return true;
                        }
                    } else if (args[2].equalsIgnoreCase("w")
                            || args[2].equalsIgnoreCase("world")) {
                        if (sender instanceof Player)
                            if (!plugin.getAPI().checkPermissions(player.getName(), player.getWorld().getName(), "mchat.user.add.world")) {
                                sender.sendMessage(Messanger.format("You don't have Permission to do that."));
                                return true;
                            }
                        try {
                            plugin.getInfoWriter().addWorld(args[3], InfoType.USER, args[4]);
                            sender.sendMessage(Messanger.format("Info Addition Successful."));
                            return true;
                        } catch (ArrayIndexOutOfBoundsException er) {
                            sender.sendMessage(Messanger.format("/mchat user add world Player World"));
                            return true;
                        }
                    } else if (args[2].equalsIgnoreCase("wVar")
                            || args[2].equalsIgnoreCase("worldVariable")) {
                        if (sender instanceof Player)
                            if (!plugin.getAPI().checkPermissions(player.getName(), player.getWorld().getName(), "mchat.user.add.wvar")) {
                                sender.sendMessage(Messanger.format("You don't have Permission to do that."));
                                return true;
                            }
                        try {
                            plugin.getInfoWriter().setWorldVar(args[3], InfoType.USER, args[4], args[5], stringArgs(args, 6));
                            sender.sendMessage(Messanger.format("Info Addition Successful."));
                            return true;
                        } catch (ArrayIndexOutOfBoundsException er) {
                            sender.sendMessage(Messanger.format("/mchat user add world Player World InfoVariable InfoValue"));
                            return true;
                        }
                    }
                }  else if (args[1].equalsIgnoreCase("s")
                        || args[1].equalsIgnoreCase("set")) {
                    if (args.length == 2) {
                        sender.sendMessage(Messanger.format("Usage for '/mchat user set':"));
                        sender.sendMessage("- /mchat user set group Player NewGroup");
                        return true;
                    } else if (args[2].equalsIgnoreCase("g")
                            || args[2].equalsIgnoreCase("group")) {
                        if (sender instanceof Player)
                            if (!plugin.getAPI().checkPermissions(player.getName(), player.getWorld().getName(), "mchat.user.set.group")) {
                                sender.sendMessage(Messanger.format("You don't have Permission to do that."));
                                return true;
                            }
                        try {
                            plugin.getInfoWriter().setGroup(args[3], args[4]);
                            sender.sendMessage(Messanger.format("Info Addition Successful."));
                            return true;
                        } catch (ArrayIndexOutOfBoundsException er) {
                            sender.sendMessage(Messanger.format("/mchat user set group Player NewGroup"));
                            return true;
                        }
                    }
                } else if (args[1].equalsIgnoreCase("r")
                        || args[1].equalsIgnoreCase("remove")) {
                    if (args.length == 2) {
                        sender.sendMessage(Messanger.format("Usage for '/mchat user remove':"));
                        sender.sendMessage("- /mchat user remove Player");
                        sender.sendMessage("- /mchat user remove Player InfoVariable");
                        sender.sendMessage("- /mchat user remove Player World");
                        sender.sendMessage("- /mchat user remove Player World InfoVariable");
                        return true;
                    } else if (args[2].equalsIgnoreCase("p")
                            || args[2].equalsIgnoreCase("player")) {
                        if (sender instanceof Player)
                            if (!plugin.getAPI().checkPermissions(player.getName(), player.getWorld().getName(), "mchat.user.remove.player")) {
                                sender.sendMessage(Messanger.format("You don't have Permission to do that."));
                                return true;
                            }
                        try {
                            plugin.getInfoWriter().removeBase(args[3], InfoType.USER);
                            sender.sendMessage(Messanger.format("Info Addition Successful."));
                            return true;
                        } catch (ArrayIndexOutOfBoundsException er) {
                            sender.sendMessage(Messanger.format("/mchat user remove Player"));
                            return true;
                        }
                    } else if (args[2].equalsIgnoreCase("iVar")
                            || args[2].equalsIgnoreCase("infoVariable")) {
                        if (sender instanceof Player)
                            if (!plugin.getAPI().checkPermissions(player.getName(), player.getWorld().getName(), "mchat.user.remove.ivar")) {
                                sender.sendMessage(Messanger.format("You don't have Permission to do that."));
                                return true;
                            }
                        try {
                            plugin.getInfoWriter().removeInfoVar(args[3], InfoType.USER, args[4]);
                            sender.sendMessage(Messanger.format("Info Addition Successful."));
                            return true;
                        } catch (ArrayIndexOutOfBoundsException er) {
                            sender.sendMessage(Messanger.format("/mchat user remove Player InfoVariable"));
                            return true;
                        }
                    } else if (args[2].equalsIgnoreCase("w")
                            || args[2].equalsIgnoreCase("world")) {
                        if (sender instanceof Player)
                            if (!plugin.getAPI().checkPermissions(player.getName(), player.getWorld().getName(), "mchat.user.remove.world")) {
                                sender.sendMessage(Messanger.format("You don't have Permission to do that."));
                                return true;
                            }
                        try {
                            plugin.getInfoWriter().removeWorld(args[3], InfoType.USER, args[4]);
                            sender.sendMessage(Messanger.format("Info Addition Successful."));
                            return true;
                        } catch (ArrayIndexOutOfBoundsException er) {
                            sender.sendMessage(Messanger.format("/mchat user remove Player World"));
                            return true;
                        }
                    } else if (args[2].equalsIgnoreCase("wVar")
                            || args[2].equalsIgnoreCase("worldVariable")) {
                        if (sender instanceof Player)
                            if (!plugin.getAPI().checkPermissions(player.getName(), player.getWorld().getName(), "mchat.user.remove.wvar")) {
                                sender.sendMessage(Messanger.format("You don't have Permission to do that."));
                                return true;
                            }
                        try {
                            plugin.getInfoWriter().removeWorldVar(args[3], InfoType.USER, args[4], args[5]);
                            sender.sendMessage(Messanger.format("Info Addition Successful."));
                            return true;
                        } catch (ArrayIndexOutOfBoundsException er) {
                            sender.sendMessage(Messanger.format("/mchat user remove Player World InfoVariable"));
                            return true;
                        }
                    }
                }
            } else if (args[0].equalsIgnoreCase("g")
                    || args[0].equalsIgnoreCase("group")) {
                if (args.length == 1) {
                    sender.sendMessage(Messanger.format("Use '/mchat group add/edit/remove' for group help."));
                    return true;
                } else if (args[1].equalsIgnoreCase("a")
                        || args[1].equalsIgnoreCase("add")) {
                    if (args.length == 2) {
                        sender.sendMessage(Messanger.format("Usage for '/mchat group add':"));
                        sender.sendMessage("- /mchat group add group Group");
                        sender.sendMessage("- /mchat group add ivar Group InfoVariable InfoValue");
                        sender.sendMessage("- /mchat group add world Group World");
                        sender.sendMessage("- /mchat group add world Group World InfoVariable InfoValue");
                        return true;
                    } else if (args[2].equalsIgnoreCase("g")
                            || args[2].equalsIgnoreCase("group")) {
                        if (sender instanceof Player)
                            if (!plugin.getAPI().checkPermissions(player.getName(), player.getWorld().getName(), "mchat.group.add.group")) {
                                sender.sendMessage(Messanger.format("You don't have Permission to do that."));
                                return true;
                            }
                        try {
                            plugin.getInfoWriter().addBase(args[3], InfoType.GROUP);
                            sender.sendMessage(Messanger.format("Info Addition Successful."));
                            return true;
                        } catch (ArrayIndexOutOfBoundsException er) {
                            sender.sendMessage(Messanger.format("/mchat group add group Group"));
                            return true;
                        }
                    } else if (args[2].equalsIgnoreCase("iVar")
                            || args[2].equalsIgnoreCase("infoVariable")) {
                        if (sender instanceof Player)
                            if (!plugin.getAPI().checkPermissions(player.getName(), player.getWorld().getName(), "mchat.group.add.ivar")) {
                                sender.sendMessage(Messanger.format("You don't have Permission to do that."));
                                return true;
                            }
                        try {
                            plugin.getInfoWriter().setInfoVar(args[3], InfoType.GROUP, args[4], stringArgs(args, 5));
                            sender.sendMessage(Messanger.format("Info Addition Successful."));
                            return true;
                        } catch (ArrayIndexOutOfBoundsException er) {
                            sender.sendMessage(Messanger.format("/mchat group add ivar Group InfoVariable InfoValue"));
                            return true;
                        }
                    } else if (args[2].equalsIgnoreCase("w")
                            || args[2].equalsIgnoreCase("world")) {
                        if (sender instanceof Player)
                            if (!plugin.getAPI().checkPermissions(player.getName(), player.getWorld().getName(), "mchat.group.add.world")) {
                                sender.sendMessage(Messanger.format("You don't have Permission to do that."));
                                return true;
                            }
                        try {
                            plugin.getInfoWriter().addWorld(args[3], InfoType.GROUP, args[4]);
                            sender.sendMessage(Messanger.format("Info Addition Successful."));
                            return true;
                        } catch (ArrayIndexOutOfBoundsException er) {
                            sender.sendMessage(Messanger.format("/mchat group add world Group World"));
                            return true;
                        }
                    } else if (args[2].equalsIgnoreCase("wVar")
                            || args[2].equalsIgnoreCase("worldVariable")) {
                        if (sender instanceof Player)
                            if (!plugin.getAPI().checkPermissions(player.getName(), player.getWorld().getName(), "mchat.group.add.wvar")) {
                                sender.sendMessage(Messanger.format("You don't have Permission to do that."));
                                return true;
                            }
                        try {
                            plugin.getInfoWriter().setWorldVar(args[3], InfoType.GROUP, args[4], args[5], stringArgs(args, 6));
                            sender.sendMessage(Messanger.format("Info Addition Successful."));
                            return true;
                        } catch (ArrayIndexOutOfBoundsException er) {
                            sender.sendMessage(Messanger.format("/mchat group add wvar Group World InfoVariable InfoValue"));
                            return true;
                        }
                    }
                } else if (args[1].equalsIgnoreCase("r")
                        || args[1].equalsIgnoreCase("remove")) {
                    if (args.length == 2) {
                        sender.sendMessage(Messanger.format("Usage for '/mchat group remove':"));
                        sender.sendMessage("- /mchat group remove Group");
                        sender.sendMessage("- /mchat group remove Group InfoVariable");
                        sender.sendMessage("- /mchat group remove Group World");
                        sender.sendMessage("- /mchat group remove Group World InfoVariable");
                        return true;
                    } else if (args[2].equalsIgnoreCase("g")
                            || args[2].equalsIgnoreCase("group")) {
                        if (sender instanceof Player)
                            if (!plugin.getAPI().checkPermissions(player.getName(), player.getWorld().getName(), "mchat.group.remove.group")) {
                                sender.sendMessage(Messanger.format("You don't have Permission to do that."));
                                return true;
                            }
                        try {
                            plugin.getInfoWriter().removeBase(args[3], InfoType.GROUP);
                            sender.sendMessage(Messanger.format("Info Addition Successful."));
                            return true;
                        } catch (ArrayIndexOutOfBoundsException er) {
                            sender.sendMessage(Messanger.format("/mchat group remove group Group"));
                            return true;
                        }
                    } else if (args[2].equalsIgnoreCase("iVar")
                            || args[2].equalsIgnoreCase("infoVariable")) {
                        if (sender instanceof Player)
                            if (!plugin.getAPI().checkPermissions(player.getName(), player.getWorld().getName(), "mchat.group.remove.ivar")) {
                                sender.sendMessage(Messanger.format("You don't have Permission to do that."));
                                return true;
                            }
                        try {
                            plugin.getInfoWriter().removeInfoVar(args[3], InfoType.GROUP, args[4]);
                            sender.sendMessage(Messanger.format("Info Addition Successful."));
                            return true;
                        } catch (ArrayIndexOutOfBoundsException er) {
                            sender.sendMessage(Messanger.format("/mchat group remove ivar Group InfoVariable"));
                            return true;
                        }
                    } else if (args[2].equalsIgnoreCase("w")
                            || args[2].equalsIgnoreCase("world")) {
                        if (sender instanceof Player)
                            if (!plugin.getAPI().checkPermissions(player.getName(), player.getWorld().getName(), "mchat.group.remove.world")) {
                                sender.sendMessage(Messanger.format("You don't have Permission to do that."));
                                return true;
                            }
                        try {
                            plugin.getInfoWriter().removeWorld(args[3], InfoType.GROUP, args[4]);
                            sender.sendMessage(Messanger.format("Info Addition Successful."));
                            return true;
                        } catch (ArrayIndexOutOfBoundsException er) {
                            sender.sendMessage(Messanger.format("/mchat group remove world Group World"));
                            return true;
                        }
                    } else if (args[2].equalsIgnoreCase("wVar")
                            || args[2].equalsIgnoreCase("worldVariable")) {
                        if (sender instanceof Player)
                            if (!plugin.getAPI().checkPermissions(player.getName(), player.getWorld().getName(), "mchat.group.remove.wvar")) {
                                sender.sendMessage(Messanger.format("You don't have Permission to do that."));
                                return true;
                            }
                        try {
                            plugin.getInfoWriter().removeWorldVar(args[3], InfoType.GROUP, args[4], args[5]);
                            sender.sendMessage(Messanger.format("Info Addition Successful."));
                            return true;
                        } catch (ArrayIndexOutOfBoundsException er) {
                            sender.sendMessage(Messanger.format("/mchat group remove wvar Group World InfoVariable"));
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    String stringArgs(String[] args, Integer startingPoint) {
        String argString = "";

        for (int i = startingPoint; i < args.length; ++i) {
            if (i == args.length - 1) {
                argString += args[i];
            } else
                argString += args[i] + " ";
        }

        return argString;
    }
}