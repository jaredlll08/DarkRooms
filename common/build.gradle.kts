import com.blamejared.darkrooms.gradle.Versions

plugins {
    java
    id("org.spongepowered.gradle.vanilla") version "0.2.1-SNAPSHOT"
    id("com.blamejared.darkrooms.default")
}


minecraft {
    version(Versions.MINECRAFT)
}

dependencies {
    compileOnly("org.spongepowered:mixin:0.8.5")
}