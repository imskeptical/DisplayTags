package me.skyyiscool.displaytags.config.spec;

import revxrsal.spec.annotation.*;

@ConfigSpec(header = {
        "----------------------------------",
        "        DisplayTags Config        ",
        "----------------------------------",
        "This is the configuration file for DisplayTags.",
        "To apply any changes you make here, run: /displaytags reload",
        "Wiki: https://github.com/imskeptical/DisplayTags/wiki"
})
public interface DisplayTagsConfigurationSpec {
    @Order(1) @Key("nametag")
    NameTagConfigurationSpec nametag();

    @Save
    void save();

    @Reload
    void reload();
}
