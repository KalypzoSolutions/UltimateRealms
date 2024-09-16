<div style="text-align: center; margin-top: 2rem">
    <img src=".github/assets/logo.jpg" alt="The logo was created with Adobe Firefly 3 on 16.09.24 with Pixel art of paradise island floating in the sky, background in retro style for 8 bit game 3" width=200 height=200>
</div>

---

# ULTIMATE REALMS - WIP 🏝️

Easy Realm Creation for Minecraft Java Edition Servers.

## Features

- [ ] Multiple Storage Methods
    - [ ] SFTP / SSH (zip)
    - [ ] Local (zip)
-  [ ] Slime Region Format Support
- [ ] Realm-World Creation
    - [ ] WorldEdit Schematic Support
    - [ ] Template Worlds
- [ ] Extendable Realm Metadata
    - Allows for custom metadata to be stored with the realm for use in other plugins.
    - e.g. a rating system

## Realm Lifecycle

**0 - Creation**
- Create a new realm by template or worldedit schematic
- Upload a world
- Set the spawn point
- Set the region
- Set the realm name
- ownership 

**1 - Loading**
- Prepare the realm for loading (PreLoadEvent)
- Load the realm into the server
- Realm is now available for players to join and explore

**2 - Running**
- Players can join the realm and interact with it
- If empty for a certain amount of time, the realm will be unloaded -> *Idle-State* 
- If the server is restarted, the realm will be unloaded

**2 - Unloading**
- Prepare the realm for unloading (PreUnloadEvent)
- Unload the realm from the server
- Realm is no longer available for players to join










