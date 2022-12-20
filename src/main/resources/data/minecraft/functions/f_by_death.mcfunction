execute as @a[scores={d2=1..}] run tellraw @a ["",{"text":"F @", "color":"red"},{"selector":"@s ","color":"red"}]
execute as @a[scores={d2=1..}] run scoreboard players set @s d2 0