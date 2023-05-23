package dev.adamraichu.compass3d;

public enum RegexGroup {
  MINECRAFT_COMPASS("^item\\.minecraft\\.compass$"),
  MINECRAFT_LODESTONE_COMPASS("^item\\.minecraft\\.lodestone_compass$");

  public final String regex;

  RegexGroup(String regex) {
    this.regex = regex;
  }
};
