select j.nombre, (select count(*) FROM PartidosEquipos as pe where (pe.equipos.jugadoresByJugadoresId.nombre=j.nombre)  and (select sum(puntos) from Rondas where (equipos.nombre=pe.equipos.nombre and partidas.id=pe.partidas.id)) is null) + 
(select count(*) 
FROM PartidosEquipos as pe 
where 
(pe.equipos.jugadoresByJugadoresId1.nombre=j.nombre) 
 and (select sum(puntos) from Rondas 
where (equipos.nombre=pe.equipos.nombre and partidas.id=pe.partidas.id)) is null) 
from 
Jugadores as j ;