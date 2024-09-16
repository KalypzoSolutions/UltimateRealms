<p align="center" style='text-align: center; margin-top: 2rem'>
<br />
<img src=".github/assets/logo.jpg" alt="The logo was created with Adobe Firefly 3 on 16.09.24 with Pixel art of paradise island floating in the sky, background in retro style for 8 bit game 3">
</p>
<h2 align="center"> ULTIMATE REALMS - WIP 🏝️ </h2>


---



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
- [ ] Options ( [see below](#options) )

---

## Different Modes explained

### A) Single-Server

It's possible when you have a single server, and you want to host multiple realms on it.

### B) Decentralized Multi-Server

With `BungeeCord-Plugin-Messaging` or `Redis-PubSub` for cross-server communication you can create a decentralized
multi-server network. This allows you to create a network of servers where each server is a realm-host.
This allows for more flexibility in scaling and managing the network.

### C) Forwarding

Let's imagine you have a server which is not a realm server, but the player should interact with his/other realms like
he is on the server, then you put the plugin in forwarding mode.
The Forwarding Mode ensures that most of the API is working as expected, but no realm is not hosted on the server.

## Options

- [ ] Force Realm
    - Ensures that on join the player will be teleported to his realm.
- [ ] Rebalancing (Decentralized Multi-Server only)
    - Rebalances some idle realms to another host to allow downscaling or simple load distribution.








