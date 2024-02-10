package io.github.adamraichu.compass3d;

public enum RegexGroup {
  MINECRAFT_COMPASS("^item\\.minecraft\\.compass$"),
  MINECRAFT_LODESTONE_COMPASS("^item\\.minecraft\\.lodestone_compass$"),
  MINECRAFT_RECOVERY_COMPASS("^item\\.minecraft\\.recovery_compass$"),
  MODDED_NETHERITE_COMPASS("^item\\.netherite_compass\\.netherite_compass$"),
  MODDED_ORE_COMPASS("^item\\.miners-compass\\.ore_compass$");

  public final String regex;

  RegexGroup(String regex) {
    this.regex = regex;
  }
};
