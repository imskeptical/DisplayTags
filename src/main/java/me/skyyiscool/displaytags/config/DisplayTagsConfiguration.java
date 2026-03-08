package me.skyyiscool.displaytags.config;

import revxrsal.spec.annotation.*;

@ConfigSpec(header = {
        "----------------------------------",
        "        DisplayTags Config        ",
        "----------------------------------",
        "This is the configuration file for DisplayTags.",
        "To apply any changes you make here, run: /displaytags reload",
        "Wiki: https://github.com/imskeptical/DisplayTags/wiki"
})
public interface DisplayTagsConfiguration {
    @Order(1) @Key("nametag")
    NameTagConfiguration nametag();

    @Save
    void save();

    @Reload
    void reload();
}
