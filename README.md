
# English

---
# FusionRep is a plugin that allows players to rate each other and compete for higher rankings

---
# Functionality

---

This plugin provides the ability to rate a player by clicking on them with a RMB in a squat. To rate opens a menu where you can choose whether to raise or lower the player's reputation

The menu has three types of display (changed in `config.yml`):
- Tiles
- Blocks
- Heads
# Commands

---

`/reputation <player nickname>` - Shows the reputation of the player whose nickname was entered

`/reputation top` - Shows the top 10 players by reputation and ranking in the team sender's top spot

`/myreputation` - Shows the reputation of the team sender

`/fusionrepreload` - Reloads the plugin configuration

---
# Configuration

``` yml
# Chat messages (CHAT MESSAGES ONLY!!) are formatted using https://docs.advntr.dev/minimessage/format.html  
  
# Database parameters
database:  
  # Database type MySQL / H2
  type: "H2"  
  # JDBC connection string 
  url: ""  
  # Username 
  user: ""  
  # Password 
  password: ""  
  
# Plugin settings
settings:  
  # Reputation menu settings
  menu:  
    # Menu style (1 - 3) 1 - Panels / 2 - Blocks / 3 - Heads 
    style: 1  
    # Menu background material
    background_material: "BLACK_STAINED_GLASS_PANE"  
    # Plus reputation head texture (Only on third style)
    plus_head_url: "http://textures.minecraft.net/texture/6c48ddfdcd6d98a1b0aa3c71e8dad4edde732a68b2b0a5ab142600dca7587c32"  
    # Minus reputation head texture (Only on third style)
    minus_head_url: "http://textures.minecraft.net/texture/6f05afec2a6ec675cd5505a8f44bb6a4d556935689528321ead4edef685f2d10"  
  
# Localization Settings 
localization:  
  # Localization of the configure reload command
  reload_command:  
    # Successful reboot message 
    reload_message: "<green>The plugin has been successfully reloaded"
  # Localization of the reputation command
  reputation_command:  
    # Incorrect syntax message 
    usage: "<red>Usage: /reputation <player / top>"  
    # Player reputation message 
    player_reputation: "<blue>%player%'s</blue> reputation: <blue>%reputation%"  
    # Message that the player is not found
    player_not_found_message: "<red>Player not found or not online"  
    # Localization of the top by reputation  
    top:  
      # Title of the reputation top list
      title: "<blue>=== Top 10 players by reputation ==="  
      # Reputation top list item
      player_in_top: "<gray>%rank%.</gray> %player% <blue>%reputation%"  
      # Sender's place in the ranking 
      sender_place: "Your place in the rating: <blue>%rank%"  
  # Localization of my reputation command
  my_reputation_command:  
    # Player reputation message
    player_reputation: "Your reputation: <blue>%reputation%"  
  # Localization of the reputation menu 
  reputation_menu:  
    # Menu title
    title: "%player%'s reputation"  
    # Message to sender if he has already voted for a player
    already_voted_message: "<red>You have already voted for this player"  
    # Message to sender about reputation increase
    plus_reputation_sender: "You have increased the player's reputation, his current reputation: <blue>%reputation%"  
    # Communicating the purpose of reputation enhancement
    plus_reputation_target: "Player <blue>%player%</blue> has increased your reputation, your current reputation is: <blue>%reputation%"  
    # Message to sender about reputation downgrade
    minus_reputation_sender: "You have lowered the player's reputation, his current reputation: <blue>%reputation%"  
    # Communicating the target of a reputation downgrade
    minus_reputation_target: "Player <blue>%player%</blue> has lowered your reputation, your current reputation is: <blue>%reputation%"  
    # Items on the menu  
    items:  
      # Reputation boost button
      plus_reputation_button:  
        # Title
        title: "+REP"  
        # Lore
        lore: "+1 to the player's reputation"  
      # Reputation downgrade button 
      minus_reputation_button:  
        # Title
        title: "-REP"  
        # Lore 
        lore: "-1 to the player's reputation"  
      # Menu exit button
      exit_button:  
        # Title
        title: "Exit"  
        # Lore 
        lore: "Close the menu"  
      # Background button  
      background:  
        # Title 
        title: ""  
        # Lore 
        lore: "Close the menu"
```

---
# Placeholders

---

`fusion_reputation` - Shows player's reputation in white color

`fusion_reputation_colored` - Shows player's reputation in color depending on reputation

---
# Permissions

---
`fusionrep.reputation.command` - Use `/reputation <player nickname>` and `/reputation top`

`fusionrep.myreputation` - use `/myreputation`

`fusionrep.reputation` - using RMB + Shift to rate a user

`fusionrep.reload` - use `/fusionrepreload`

---
---
---
---
---
# Русский язык

---

# FusionRep - плагин, который позволяет игрокам оценивать друг друга и соревноваться за более высокий рейтинг

---
# Функционал

---

Данный плагин предоставляет возможность оценки игроком, путём нажатия по нему ПКМ в приседе. Для оценки открывается меню, в котором можно выбрать, что нужно сделать: повысить или понизить репутацию игрока

У меню есть три вида отображения (изменяется в `config.yml`):
- Плитки
- Блоки
- Головы
# Команды

---

`/reputation <ник игрока>` - Показывает репутацию игрока, чей ник был введён

`/reputation top` - Показывает топ-10 игроков по репутации и рейтинг в топе отправителя команды

`/myreputation` - Показывает репутацию отправителя команды

`/fusionrepreload` - Перезагружает конфигурацию плагина

---
# Конфигурация

---

``` yml
# Сообщения в чат (ТОЛЬКО СООБЩЕНИЯ В ЧАТ!!) форматируются с помощью https://docs.advntr.dev/minimessage/format.html  
  
# Параметры датабазы  
database:  
  # Тип датабазы MySQl / H2  
  type: "H2"  
  # Ключ подключения JDBC  
  url: ""  
  # Имя пользователя  
  user: ""  
  # Пароль  
  password: ""  
  
# Настройки плагина  
settings:  
  # Настройки меню репутации  
  menu:  
    # Стиль меню (1 - 3) 1 - Панели / 2 - Блоки / 3 - Головы  
    style: 1  
    # Материал заднего фона меню  
    background_material: "BLACK_STAINED_GLASS_PANE"  
    # Текстура головы повышения репутации (Только при третьем стиле)  
    plus_head_url: "http://textures.minecraft.net/texture/6c48ddfdcd6d98a1b0aa3c71e8dad4edde732a68b2b0a5ab142600dca7587c32"  
    # Текстура головы понижения репутации (Только при третьем стиле)  
    minus_head_url: "http://textures.minecraft.net/texture/6f05afec2a6ec675cd5505a8f44bb6a4d556935689528321ead4edef685f2d10"  
  
# Настройки локализации  
localization:  
  # Локализация команды перезагрузки конфига  
  reload_command:  
    # Сообщение об успешной перезагрузке  
    reload_message: "<green>Плагин был успешно перезагружен"
  # Локализация команды репутации  
  reputation_command:  
    # Сообщение о неправильном синтаксисе  
    usage: "<red>Использование: /reputation <player / top>"  
    # Сообщение с репутацией игрока  
    player_reputation: "Репутация игрока <blue>%player%</blue>: <blue>%reputation%"  
    # Сообщение о том, что игрок не найден  
    player_not_found_message: "<red>Игрок не найден, или он не в сети"  
    # Локализация топа по репутации  
    top:  
      # Оглавление списка топа репутации  
      title: "<blue>=== Топ-10 игроков по репутации ==="  
      # Пункт списка топа репутации  
      player_in_top: "<gray>%rank%.</gray> %player% <blue>%reputation%"  
      # Место отправителя в рейтинге  
      sender_place: "Ваше место в топе: <blue>%rank%"  
  # Локализация команды своей репутации  
  my_reputation_command:  
    # Сообщение с репутацией игрока  
    player_reputation: "Ваша репутация: <blue>%reputation%"  
  # Локализация меню репутации  
  reputation_menu:  
    # Название меню  
    title: "Репутация игрока %player%"  
    # Сообщение отправителю, если он уже голосовал за игрока  
    already_voted_message: "<red>Вы уже голосовали за игрока"  
    # Сообщение отправителю о повышении репутации  
    plus_reputation_sender: "Вы повысили репутацию игрока <blue>%player%</blue>, его текущая репутация: <blue>%reputation%"  
    # Сообщение цели о повышении репутации  
    plus_reputation_target: "Игрок <blue>%player%</blue> повысил вашу репутацию, ваша текущая репутация: <blue>%reputation%"  
    # Сообщение отправителю о понижении репутации  
    minus_reputation_sender: "Вы понизили репутацию игрока <blue>%player%</blue>, его текущая репутация: <blue>%reputation%"   
    # Сообщение цели о понижении репутации  
    minus_reputation_target: "Игрок <blue>%player%</blue> понизил вашу репутацию, ваша текущая репутация: <blue>%reputation%"  
    # Предметы в меню  
    items:  
      # Кнопка повышения репутации  
      plus_reputation_button:  
        # Название  
        title: "+РЕП"  
        # Описание  
        lore: "+1 к репутации игрока"  
      # Кнопка понижения репутации  
      minus_reputation_button:  
        # Название  
        title: "-РЕП"  
        # Описание  
        lore: "-1 к репутации игрока"  
      # Кнопка выхода из меню  
      exit_button:  
        # Название  
        title: "Выйти"  
        # Описание  
        lore: "Закрыть меню"  
      # Фоновая кнопка  
      background:  
        # Название  
        title: ""  
        # Описание  
        lore: "Закрыть меню"
```

---
# Плейсхолдеры

---

`fusion_reputation` - Показывает репутацию игрока в белом цвете

`fusion_reputation_colored` - Показывает репутацию игрока в цвете в зависимости от репутации

---
# Права

---
`fusionrep.reputation.command` - использование `/reputation <ник игрока>` и `/reputation top`

`fusionrep.myreputation` - использование `/myreputation`

`fusionrep.reputation` - использование ПКМ + Shift для оценки пользователя

`fusionrep.reload` - использование `/fusionrepreload`
