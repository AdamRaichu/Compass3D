package dev.adamraichu.compass3d;

public enum RegexGroup {
  MINECRAFT_PLAYER_HEAD("^block\\.minecraft\\.player_head$"),
  MINECRAFT_SHULKER("^block\\.minecraft\\..*shulker_box$"),
  MINECRAFT_COMPASS("^item\\.minecraft\\.lodestone_compass$");

  public final String regex;

  RegexGroup(String regex) {
    this.regex = regex;
  }
};
