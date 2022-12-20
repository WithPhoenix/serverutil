execute as @a[nbt={Inventory:[{id:"minecraft:structure_void"}]}] store result score @s frame_count run clear @s minecraft:structure_void
give @a[scores={frame_count=1..}] item_frame{display:{Lore:['{"text":"invisible","color":"aqua"}']},EntityTag:{Invisible:1b}} 1
scoreboard players remove @a[scores={frame_count=1..}] frame_count 1
execute if entity @a[scores={frame_count=1..}] run function minecraft:to_frame
