scoreboard objectives add frame_count dummy
scoreboard objectives add dumb dummy
scoreboard objectives add deaths deathCount
scoreboard objectives add d2 deathCount
scoreboard objectives setdisplay list deaths
team add afk
team modify afk prefix {"text":"[afk] ","color":"yellow"}