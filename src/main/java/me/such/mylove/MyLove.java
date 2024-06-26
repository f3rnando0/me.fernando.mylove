package me.such.mylove;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.entity.EntityPortalEnterEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;
import org.bukkit.*;

import java.util.List;
import java.util.Objects;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;


public final class MyLove extends JavaPlugin implements Listener {
    Challegens challegens = new Challegens();
    ChallengesEntities challengesEntities = new ChallengesEntities();
    Third third = new Third();

    @Override
    public void onEnable() {
        System.out.println("[MyLove] Plugin enabled");

        getServer().getPluginManager().registerEvents(this, this);
        Location spawn = new Location(getServer().getWorld("world"), -1.5F, -60, 0.513F, -89.8F, -0.9F);
        getServer().getWorld("world").setSpawnLocation(spawn);

        new BukkitRunnable() {
            public void run() {
                Bukkit.getServer().getWorld("world").setTime(0L);
            }
        }.runTaskTimer(this, 0L, 100L);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) throws InterruptedException {
        Location spawn = new Location(event.getPlayer().getWorld(), -1.5F, -60, 0.513F, -89.8F, -0.9F);
        event.getPlayer().teleport(spawn);
        ChallengesLocations challengesLocations = new ChallengesLocations(event.getPlayer().getWorld());
        event.getPlayer().setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10, 90);
        event.getPlayer().setWalkSpeed(0.2F);

        long delay = 65;

        event.getPlayer().sendTitle("§fSeja bem-vinda, §dmeu amor!", "§fVocê vai se divertir muito aqui!", 10, 45, 10);

        new BukkitRunnable(){

            @Override
            public void run() {
                event.getPlayer().sendTitle("§fSeu §aobjetivo:", "§fCompletar §l§6todos §fos desafios.", 10, 45, 10);
                event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10, 90);
            }

        }.runTaskLater(this, delay);

        new BukkitRunnable(){

            @Override
            public void run() {
                event.getPlayer().sendTitle("§fE se conseguir...", "§fResgatar o seu §l§bpresentinho no final.", 10, 45, 10);
                event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10, 90);
            }

        }.runTaskLater(this, delay * 2);
    }

    boolean sentFireworks = false;

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        ChallengesLocations challengesLocations = new ChallengesLocations(e.getPlayer().getWorld());
        if(challegens.getLevel() == 2) {
            if(e.getTo().getBlock().getType() == Material.WATER) {
                System.out.println("[MyLove] Player fell");
                if(challegens.getSecondLevelLastCheckpoint() == null) {
                    e.getPlayer().teleport(challengesLocations.getSecond());
                    return;
                };
                e.getPlayer().teleport(challegens.getSecondLevelLastCheckpoint());
            }

            if(e.getTo().getBlock().getType() == Material.CHERRY_SLAB) {
                if(!sentFireworks) {
                    Firework fw = (Firework) e.getPlayer().getWorld().spawnEntity(e.getPlayer().getLocation(), EntityType.FIREWORK);

                    FireworkMeta fwm = fw.getFireworkMeta();

                    fwm.setPower(2);
                    fwm.addEffect(FireworkEffect.builder().withColor(Color.PURPLE).flicker(true).build());
                    fw.setFireworkMeta(fwm);

                    fw.detonate();

                    e.getPlayer().sendTitle("§9§lParabéns!!!", "§fVocê conseguiu meu amor! Estamos quase lá...", 10, 45, 10);
                    e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10, 90);
                    sentFireworks = true;
                }
            }
        }
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        if(e.getMessage().equals("/cc")) {
            Location loc = new Location(e.getPlayer().getWorld(), 15, -60, 0);
            e.getPlayer().teleport(loc);
        }
    }

    @EventHandler
    public void onEntityPortalEnter(EntityPortalEnterEvent e) {
        if(e.getEntity() instanceof Player) {
            Location location = e.getEntity().getLocation();
            ChallengesLocations challengesLocations = new ChallengesLocations(e.getEntity().getWorld());
            Location firstChallenge = challengesLocations.getFirst();
            Location thirdChallenge = challengesLocations.getThird();

            if(location.getBlockX() == 15 && location.getBlockZ() == -1) {
                boolean isTeleported = e.getEntity().teleport(firstChallenge);
                challegens.setLevel(1);
                challegens.resetFirstLevelPoints();
            } else if(location.getBlockX() == 15 && location.getBlockZ() == 1) {
                boolean isTeleported = e.getEntity().teleport(firstChallenge);
                challegens.setLevel(1);
                challegens.resetFirstLevelPoints();
            } else if(location.getBlockX() == 15 && location.getBlockZ() == 0) {
                challegens.setLevel(1);
                challegens.resetFirstLevelPoints();
                boolean isTeleported = e.getEntity().teleport(firstChallenge);
            }

            if(location.getBlockX() == -143 && location.getBlockZ() == 52) {
                challegens.setLevel(3);
                challegens.resetCorrectThirdLevel();
                boolean isTeleported = e.getEntity().teleport(thirdChallenge);
            } else if(location.getBlockX() == -142 && location.getBlockZ() == 52) {
                challegens.setLevel(3);
                challegens.resetCorrectThirdLevel();
                boolean isTeleported = e.getEntity().teleport(thirdChallenge);
            } else if(location.getBlockX() == -143 && location.getBlockZ() == 55) {
                challegens.setLevel(3);
                challegens.resetCorrectThirdLevel();
                boolean isTeleported = e.getEntity().teleport(thirdChallenge);
            } else if(location.getBlockX() == -142 && location.getBlockZ() == 52) {
                challegens.setLevel(3);
                challegens.resetCorrectThirdLevel();
                boolean isTeleported = e.getEntity().teleport(thirdChallenge);
            } else if(location.getBlockX() == -143 && location.getBlockZ() == 54) {
                challegens.setLevel(3);
                challegens.resetCorrectThirdLevel();
                boolean isTeleported = e.getEntity().teleport(thirdChallenge);
            } else if(location.getBlockX() == -142 && location.getBlockZ() == 55) {
                challegens.setLevel(3);
                challegens.resetCorrectThirdLevel();
                boolean isTeleported = e.getEntity().teleport(thirdChallenge);
            } else if(location.getBlockX() == -142 && location.getBlockZ() == 53) {
                challegens.setLevel(3);
                challegens.resetCorrectThirdLevel();
                boolean isTeleported = e.getEntity().teleport(thirdChallenge);
            } else if(location.getBlockX() == -142 && location.getBlockZ() == 54) {
                challegens.setLevel(3);
                challegens.resetCorrectThirdLevel();
                boolean isTeleported = e.getEntity().teleport(thirdChallenge);
            }

            switch(challegens.getLevel()) {
                case 1:
                    Scoreboard board = getServer().getScoreboardManager().getMainScoreboard();

                    Objective objective = board.getObjective("first");
                    if(objective == null) {
                        objective = board.registerNewObjective("first", "dummy", ChatColor.LIGHT_PURPLE + "☆ Primeiro desafio", RenderType.HEARTS);
                    } else {
                        objective.setDisplayName(ChatColor.LIGHT_PURPLE + "☆ Primeiro desafio");
                        board.getEntries().forEach(board::resetScores);
                    }

                    objective.setDisplaySlot(DisplaySlot.SIDEBAR);

                    Score score = objective.getScore(ChatColor.WHITE + "╚ Pontuação: " + ChatColor.DARK_PURPLE + challegens.getFirstLevelPoints());
                    score.setScore(1);

                    ((Player) e.getEntity()).setScoreboard(board);
                    ((Player) e.getEntity()).getInventory().clear();
                    long delay = 65;

                    challegens.setLevel(1);
                    Player player = (Player) e.getEntity();
                    player.sendTitle(ChatColor.AQUA + "Primeiro desafio", "Consiste em acertar as vacas!", 10, 45, 10);
                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10, 90);

                    new BukkitRunnable(){

                        @Override
                        public void run() {
                            player.sendTitle("§fAcerte §a5 §fvacas.", "§fSei que você vai achar difícil, então vou facilitar.", 10, 45, 10);
                            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10, 90);
                        }

                    }.runTaskLater(this, delay);

                    new BukkitRunnable(){

                        @Override
                        public void run() {
                            Inventory inventory = player.getInventory();
                            if(!inventory.contains(Material.BOW)) {
                                ItemStack bow = new ItemStack(Material.BOW);
                                ItemMeta bowItemMeta = bow.getItemMeta();
                                bowItemMeta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
                                bowItemMeta.addEnchant(Enchantment.ARROW_DAMAGE, 100, true);
                                bow.setItemMeta(bowItemMeta);
                                player.getInventory().addItem(bow);
                                player.updateInventory();
                            }
                        }

                    }.runTaskLater(this, delay * 2);

                    challegens.getFirstChallengeCows().clear();

                    List<Entity> list = player.getNearbyEntities(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ());
                    list.forEach((Entity et) -> {
                        if(et.getType() == EntityType.MINECART) {
                            challengesEntities.setMinecart(et);
                            List<Entity> passenger = et.getPassengers();
                            if(passenger.isEmpty()) {
                                Entity cow = et.getWorld().spawnEntity(et.getLocation(), EntityType.COW);
                                et.addPassenger(cow);
                                if(!challegens.getFirstChallengeCows().contains(cow)) {
                                    challegens.addFirstChallengeCows(cow);
                                    System.out.println("[MyLove] Challenge 1 cow added");
                                }
                            } else {
                                passenger.forEach((Entity ent) -> {
                                    if(ent.getType() == EntityType.COW) {
                                        if(!challegens.getFirstChallengeCows().contains(ent)) {
                                            challegens.addFirstChallengeCows(ent);
                                            System.out.println("[MyLove] Challenge 1 cow added");
                                        }
                                    }
                                });
                            }
                        }
                    });
                case 3:

            }
        }
    }

    @EventHandler
    public void OnEntityDeath(EntityDeathEvent e) {
        if(e.getEntity().getType() == EntityType.COW) {
            e.getDrops().clear();
            e.setDroppedExp(0);
            if(challegens.getFirstChallengeCows().contains(e.getEntity())) {
                System.out.println("[MyLove] Challenge 1 cow died");
                challegens.removeFirstChallengeCows(e.getEntity());
                Scoreboard board = (Scoreboard) Objects.requireNonNull(e.getEntity().getKiller()).getPlayer().getScoreboard();

                if(challegens.getFirstLevelPoints() == 5) {
                    List<Entity> cows = challegens.getFirstChallengeCows();
                    cows.forEach(cows::remove);
                    challegens.setLevel(2);

                    Objective score = board.getObjective("first");
                    board.getEntries().forEach(board::resetScores);

                    Score v2 = score.getScore(ChatColor.WHITE + "╚ Pontuação: " + ChatColor.DARK_PURPLE + challegens.getFirstLevelPoints());
                    v2.setScore(1);

                    Firework fw = (Firework) challengesEntities.getMinecart().getWorld().spawnEntity(challengesEntities.getMinecart().getLocation(), EntityType.FIREWORK);

                    FireworkMeta fwm = fw.getFireworkMeta();

                    fwm.setPower(2);
                    fwm.addEffect(FireworkEffect.builder().withColor(Color.PURPLE).flicker(true).build());
                    fw.setFireworkMeta(fwm);

                    fw.detonate();

                    System.out.println("[MyLove] Detonating firework");

                    new BukkitRunnable(){

                        @Override
                        public void run() {
                            e.getEntity().getKiller().sendTitle(ChatColor.GOLD + "Parabéns!!", "§fVocê conseguiu. Vamos para o próximo.", 10, 25, 10);
                            e.getEntity().getKiller().playSound(e.getEntity().getKiller().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10, 90);
                        }

                    }.runTaskLater(this, 20);

                    new BukkitRunnable(){

                        @Override
                        public void run() {
                            ChallengesLocations challengesLocations = new ChallengesLocations(e.getEntity().getKiller().getWorld());
                            challegens.setLevel(2);
                            e.getEntity().getKiller().getInventory().clear();
                            e.getEntity().getKiller().teleport(challengesLocations.getSecond());
                        }

                    }.runTaskLater(this, 65);
                    return;
                }
                Objective score = board.getObjective("first");
                board.getEntries().forEach(board::resetScores);


                Score v2 = score.getScore(ChatColor.WHITE + "╚ Pontuação: " + ChatColor.DARK_PURPLE + challegens.getFirstLevelPoints());
                v2.setScore(1);

                System.out.println("[MyLove] First level points: " + challegens.getFirstLevelPoints());
                Entity minecart = challengesEntities.getMinecart();
                Cow cow = e.getEntity().getWorld().spawn(minecart.getLocation(), Cow.class);

                challegens.addFirstChallengeCows(cow);
                minecart.addPassenger(cow);
                System.out.println("[MyLove] Challenge 1 cow spawned");
            }
        }
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
            if(event.getHitEntity() != null) {
                if (event.getHitEntity().getType() == EntityType.MINECART) {
                    event.setCancelled(true);
                }
            }
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        ChallengesLocations challegensLocations = new ChallengesLocations(event.getPlayer().getWorld());
        if(event.getTo().equals(challegensLocations.getSecond())) {
            if(challegens.getLevel() == 2) {
                challegens.resetSecondCheckpointsPressed();
                challegens.resetSecondLastCheckpoint();
                Scoreboard board = getServer().getScoreboardManager().getMainScoreboard();

                Objective objective = board.getObjective("second");
                if(objective == null) {
                    objective = board.registerNewObjective("second", "dummy", ChatColor.AQUA + "☆ Segundo desafio", RenderType.HEARTS);
                } else {
                    objective.setDisplayName(ChatColor.AQUA + "☆ Segundo desafio");
                    board.getEntries().forEach(board::resetScores);
                }

                objective.setDisplaySlot(DisplaySlot.SIDEBAR);

                Score s1 = objective.getScore(ChatColor.WHITE + "╚ Checkpoints: " + ChatColor.AQUA + challegens.getSecondLevelCheckpointsPressed().size() + "/4");

                s1.setScore(1);

                event.getPlayer().setScoreboard(board);

                event.getPlayer().sendTitle(ChatColor.GREEN + "Segundo desafio", "§fBem simples, §4parkour. §fPise nas placas pra pegar o checkpoint.");
                event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10, 90);
            }
        }
        if(event.getTo().equals(challegensLocations.getThird())) {
            if(challegens.getLevel() == 3) {
                Scoreboard board = getServer().getScoreboardManager().getMainScoreboard();

                Objective objective = board.getObjective("third");
                if (objective == null) {
                    objective = board.registerNewObjective("third", "dummy", ChatColor.DARK_GRAY + "☆ Terceiro desafio", RenderType.HEARTS);
                } else {
                    objective.setDisplayName(ChatColor.DARK_GRAY + "☆ Terceiro desafio");
                    board.getEntries().forEach(board::resetScores);
                }

                objective.setDisplaySlot(DisplaySlot.SIDEBAR);

                Score s1 = objective.getScore(ChatColor.WHITE + "╚ Acertos: " + ChatColor.DARK_GRAY + challegens.getThirdLevelAcertos() + "/3");

                s1.setScore(1);

                event.getPlayer().setScoreboard(board);

                event.getPlayer().sendTitle(ChatColor.DARK_GRAY + "Terceiro desafio", "§eQuiz. §fAcerte §4todas §fas perguntas.", 10, 25, 10);
                event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10, 90);

                event.getPlayer().getInventory().clear();
                ItemStack item = new ItemStack(Material.RED_CONCRETE);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName("§4Errado");
                item.setItemMeta(meta);

                event.getPlayer().getInventory().addItem(item);

                ItemStack item2 = new ItemStack(Material.LIME_CONCRETE);
                ItemMeta meta2 = item2.getItemMeta();
                meta2.setDisplayName("§aCerto");
                item2.setItemMeta(meta2);

                event.getPlayer().getInventory().addItem(item2);

                String msg = third.getQuestion(challegens.getCurrentQuestionThirdLevel());

                new BukkitRunnable(){

                    @Override
                    public void run() {
                        event.getPlayer().sendTitle("§6Primeira:", msg, 10, 55, 10);
                        event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10, 90);
                    }

                }.runTaskLater(this, 45);
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if(e.getAction().equals(Action.PHYSICAL)) {
            ChallengesLocations challengesLocations = new ChallengesLocations(e.getPlayer().getWorld());

            Location pressed = challengesLocations.getSecondWhichPlate(e.getPlayer().getLocation().getBlockX(), e.getPlayer().getLocation().getBlockZ(), challegens);
            if(!challegens.getSecondLevelCheckpointsPressed().contains(pressed)) challegens.addSecondLevelCheckpointsPressed(pressed);

            challegens.setSecondLevelLastCheckpoint(pressed);

            Scoreboard board = (Scoreboard) Objects.requireNonNull(e.getPlayer().getScoreboard());

            Objective score = board.getObjective("second");
            board.getEntries().forEach(board::resetScores);

            Score s1 = score.getScore(ChatColor.WHITE + "╚ Checkpoints: " + ChatColor.AQUA + challegens.getSecondLevelCheckpointsPressed().size() + "/4");

            s1.setScore(1);
        }

        if(e.getAction().equals(Action.RIGHT_CLICK_AIR)) {;
            if(challegens.getLevel() == 3 && e.getPlayer().getInventory().getItemInMainHand().getType() == Material.RED_CONCRETE) {
                if(challegens.getCurrentQuestionThirdLevel() == 1) {
                    challegens.increaseThirdLevelAcertos();
                    Scoreboard board = getServer().getScoreboardManager().getMainScoreboard();

                    Objective objective = board.getObjective("third");
                    if (objective == null) {
                        objective = board.registerNewObjective("third", "dummy", ChatColor.DARK_GRAY + "☆ Terceiro desafio", RenderType.HEARTS);
                    } else {
                        objective.setDisplayName(ChatColor.DARK_GRAY + "☆ Terceiro desafio");
                        board.getEntries().forEach(board::resetScores);
                    }

                    objective.setDisplaySlot(DisplaySlot.SIDEBAR);

                    Score s1 = objective.getScore(ChatColor.WHITE + "╚ Acertos: " + ChatColor.DARK_GRAY + challegens.getThirdLevelAcertos() + "/3");

                    s1.setScore(1);

                    e.getPlayer().setScoreboard(board);

                    challegens.increaseThirdLevelQuestion();

                    String msg = Third.getQuestion(challegens.getCurrentQuestionThirdLevel());

                    new BukkitRunnable(){
                        @Override
                        public void run() {
                            e.getPlayer().sendTitle("§4Segunda:", msg, 10, 55, 10);
                            e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10, 90);
                        }

                    }.runTaskLater(this, 45);
                    return;
                } else if(challegens.getCurrentQuestionThirdLevel() == 2) {
                    challegens.increaseThirdLevelQuestion();

                    String msg = Third.getQuestion(challegens.getCurrentQuestionThirdLevel());

                    new BukkitRunnable(){
                        @Override
                        public void run() {
                            e.getPlayer().sendTitle("§4Última:", msg, 10, 55, 10);
                            e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10, 90);
                        }

                    }.runTaskLater(this, 45);
                } else if(challegens.getCurrentQuestionThirdLevel() == 3) {
                    e.getPlayer().teleport(new Location(e.getPlayer().getWorld(), -260.49F, 23, 356.562F, -180, -2));
                    e.getPlayer().setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
                    Firework fw = (Firework) e.getPlayer().getWorld().spawnEntity(e.getPlayer().getLocation(), EntityType.FIREWORK);

                    FireworkMeta fwm = fw.getFireworkMeta();

                    fwm.setPower(2);
                    fwm.addEffect(FireworkEffect.builder().withColor(Color.PURPLE).flicker(true).build());
                    fw.setFireworkMeta(fwm);

                    fw.detonate();
                    e.getPlayer().sendTitle(ChatColor.LIGHT_PURPLE + "Você conseguiu, meu amor!!!", "Estou muito orgulhoso de você.", 10, 55, 10);
                    e.getPlayer().getInventory().clear();

                    new BukkitRunnable(){

                        @Override
                        public void run() {
                            e.getPlayer().sendTitle(ChatColor.GOLD + "Seu presente vai", "§fser descoberto em 1", 10, 25, 10);
                            e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10, 90);
                        }

                    }.runTaskLater(this, 75);
                    new BukkitRunnable(){

                        @Override
                        public void run() {
                            e.getPlayer().sendTitle(ChatColor.GOLD + "Seu presente vai", "§fser descoberto em 2", 10, 25, 10);
                            e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10, 90);
                        }

                    }.runTaskLater(this, 95);
                    new BukkitRunnable(){

                        @Override
                        public void run() {
                            e.getPlayer().sendTitle(ChatColor.GOLD + "Seu presente vai", "§fser descoberto em 3", 10, 25, 10);
                            e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10, 90);
                        }

                    }.runTaskLater(this, 115);
                    new BukkitRunnable(){

                        @Override
                        public void run() {
                            TextComponent message1 = new TextComponent("E finalmente você conseguiu completar o desafio :)\n");
                            TextComponent message2 = new TextComponent("Tudo aqui foi feito com muito carinho para você, meu amor. Tive de aprender do 0 a como programar plugins de Minecraft, mas no fim das contas não era tão difícil quanto eu pensava.\n");
                            TextComponent message3 = new TextComponent("Espero que você tenha gostado do seu presentinho, meu bebê. Você é a coisa mais preciosa para mim, independente de qualquer coisa! Eu te amo, juju bebê nenem linda princesinha amor anjinho.\n");
                            TextComponent link = new TextComponent("Clique aqui para ver o presente");

                            link.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://localhost:3000"));
                            link.setBold(true);
                            link.setColor(net.md_5.bungee.api.ChatColor.GOLD);

                            e.getPlayer().spigot().sendMessage(message1);
                            e.getPlayer().spigot().sendMessage(message2);
                            e.getPlayer().spigot().sendMessage(message3);
                            e.getPlayer().spigot().sendMessage(link);
                        }

                    }.runTaskLater(this, 135);
                }
            } else if(challegens.getLevel() == 3 && e.getPlayer().getInventory().getItemInMainHand().getType() == Material.LIME_CONCRETE) {
                if(challegens.getCurrentQuestionThirdLevel() == 1) {
                    challegens.increaseThirdLevelQuestion();

                    String msg = Third.getQuestion(challegens.getCurrentQuestionThirdLevel());

                    new BukkitRunnable(){
                        @Override
                        public void run() {
                            e.getPlayer().sendTitle("§4Segunda:", msg, 10, 55, 10);
                            e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10, 90);
                        }

                    }.runTaskLater(this, 45);
                } else if(challegens.getCurrentQuestionThirdLevel() == 2) {
                    challegens.increaseThirdLevelAcertos();
                    Scoreboard board = getServer().getScoreboardManager().getMainScoreboard();

                    Objective objective = board.getObjective("third");
                    if (objective == null) {
                        objective = board.registerNewObjective("third", "dummy", ChatColor.DARK_GRAY + "☆ Terceiro desafio", RenderType.HEARTS);
                    } else {
                        objective.setDisplayName(ChatColor.DARK_GRAY + "☆ Terceiro desafio");
                        board.getEntries().forEach(board::resetScores);
                    }

                    objective.setDisplaySlot(DisplaySlot.SIDEBAR);

                    Score s1 = objective.getScore(ChatColor.WHITE + "╚ Acertos: " + ChatColor.DARK_GRAY + challegens.getThirdLevelAcertos() + "/3");

                    s1.setScore(1);

                    e.getPlayer().setScoreboard(board);

                    challegens.increaseThirdLevelQuestion();

                    String msg = Third.getQuestion(challegens.getCurrentQuestionThirdLevel());

                    new BukkitRunnable(){
                        @Override
                        public void run() {
                            e.getPlayer().sendTitle("§4Última:", msg, 10, 55, 10);
                            e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10, 90);
                        }

                    }.runTaskLater(this, 45);
                    return;
                } else if(challegens.getCurrentQuestionThirdLevel() == 3) {
                    e.getPlayer().kickPlayer("Tudo errado. Vai ter que recomeçar! :)");
                }
            }
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        if(e.getEntity().getType() == EntityType.PLAYER) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBreakBlock(BlockBreakEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent e) {
        e.setCancelled(true);
    }
}
