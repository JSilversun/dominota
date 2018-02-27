/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import modelo.*;
import org.hibernate.HibernateException;
import java.lang.String;
import java.math.BigDecimal;

public class Operaciones {
    public void AgregarUsuario(Jugadores jugador){
      try {
      Equipos equipo_individual=new Equipos(jugador);
      SessionFactory sesion = NewHibernateUtil.getSessionFactory();
      Session session;
      session=sesion.openSession();
      session.beginTransaction();
      session.save(jugador);
      session.save(equipo_individual);
      session.getTransaction().commit();
      session.close();
      JOptionPane.showMessageDialog(null,"Crado correctamente");
      } catch (Exception e) {
              JOptionPane.showMessageDialog(null,"El Juagador ya existe");
	      System. out. println("Usuario ya existe");          
      }
     
    }
    
    public void AgregarEquipo(Equipos equipo){
        try {
            SessionFactory sesion = NewHibernateUtil.getSessionFactory();
            Session session;
            session=sesion.openSession();
            session.beginTransaction();
            session.save(equipo);
            session.getTransaction().commit();
            session.close();
            JOptionPane.showMessageDialog(null,"Equipo creado correctamente");
        } catch (Exception e) {
              JOptionPane.showMessageDialog(null,"El equipo ya exise");
	      System. out. println(e);          
        }
    }
    
    public ArrayList<Jugadores> ConsultarJugadores(){
        ArrayList<Jugadores> jugadores = null;
        try {
            SessionFactory sesion = NewHibernateUtil.getSessionFactory();
            Session session;
            session=sesion.openSession();
            session.beginTransaction();
            Query query = session.createQuery("from Jugadores");
            jugadores = (ArrayList<Jugadores>)query.list();
            for(Jugadores jugador : jugadores) {
                System.out.println(jugador.getNombre());
            }
        }
        catch(Exception e) {
            
        }
        return jugadores;
    }
    public void actualizarGanador(Partidas partida, Equipos e){
        try {
            SessionFactory sesion = NewHibernateUtil.getSessionFactory();
            Session session;
            session=sesion.openSession();
            session.beginTransaction();
            partida.setEquipos(e);
            session.update(partida);
            session.getTransaction().commit();
            session.close();
            //JOptionPane.showMessageDialog(null, "Partida creada con exito!");
        }
        catch(Exception ex) {
            System.out.println("error");
        }
    }
    public Partidas crearPartida(Set<Equipos> equiposes, int puntos){
        try {
            SessionFactory sesion = NewHibernateUtil.getSessionFactory();
            Session session;
            session=sesion.openSession();
            session.beginTransaction();
            Partidas partida = new Partidas(new Date(),new BigDecimal(puntos));
            
            partida.setEquiposes(equiposes);
            session.save(partida);
            session.getTransaction().commit();
            session.close();
            return partida;
            //JOptionPane.showMessageDialog(null, "Partida creada con exito!");
        }
        catch(Exception e) {
            System.out.println("error");
        }
        return null;
    }
    
    public Jugadores InformacionJugador(String nombre){
        try {
            SessionFactory sesion = NewHibernateUtil.getSessionFactory();
            Session session;
            session=sesion.openSession();
            session.beginTransaction();
            Query query = session.createQuery("from Jugadores where nombre =:nombre");
            query.setParameter("nombre", nombre);
            Jugadores jugador = (Jugadores) query.uniqueResult();
            return jugador;
        }
        catch(Exception e) {
            
        }
        return null;
    }
    
    public ArrayList<Equipos> ConsultarEquiposes(){
        ArrayList<Equipos> equipos = null;
        try {
            SessionFactory sesion = NewHibernateUtil.getSessionFactory();
            Session session;
            session=sesion.openSession();
            session.beginTransaction();
            Query query = session.createQuery("from Equipos where jugadores_id1 <> null");
            equipos = (ArrayList<Equipos>)query.list();
            for(Equipos equipo : equipos) {
                System.out.println(equipo.getNombre());
            }
        }
        catch(Exception e) {
            
        }
        return equipos;
    }
    
    public ArrayList<Equipos> ConsultarIndividual(){
        ArrayList<Equipos> equipos = null;
        try {
            SessionFactory sesion = NewHibernateUtil.getSessionFactory();
            Session session;
            session=sesion.openSession();
            session.beginTransaction();
            Query query = session.createQuery("from Equipos where jugadores_id1 = null");
            equipos = (ArrayList<Equipos>)query.list();
            for(Equipos equipo : equipos) {
                System.out.println(equipo.getNombre());
            }
        }
        catch(Exception e) {
            
        }
        return equipos;
    }
    
    public Equipos ObjetoEquipos(String nombre){
       
        try {
            SessionFactory sesion = NewHibernateUtil.getSessionFactory();
            Session session;
            session=sesion.openSession();
            session.beginTransaction();
            Query query = session.createQuery("from Equipos where nombre =?");
            query.setString(0,nombre);
            Equipos equipo= (Equipos) query.uniqueResult();
            session.close();
            return equipo;
        }
        catch(Exception e) {
            System.out.println("Algo salio mal");
            return null;
        }
        
    } 
    public List<Object[]> totalEnPartida(BigDecimal partida_id, BigDecimal equipo_id){
    //try {
            SessionFactory sesion = NewHibernateUtil.getSessionFactory();
            Session session;
            session=sesion.openSession();
            session.beginTransaction();
            //"SELECT e.id, SUM(r.puntos),p.id from Rondas r join r.equipos e join r.partidas p where r.partidas.id=? group by r.equipos.id, r.partidas.id"
            Query query = session.createQuery(
                    "SELECT e.id, SUM(r.puntos)from Rondas r join r.equipos e join r.partidas p where r.partidas.id=? and r.equipos.id=? group by e.id, p.id");
            query.setBigDecimal(0,partida_id);
            query.setBigDecimal(1,equipo_id);
            List<Object[]>result=query.list();
            session.close();
            return result;
            
            
        //}
        //catch(Exception e) {
        //    System.out.println("Algo salio mal");
        //}
        //return null;
    }
    public void agregarRonda(Partidas p, Equipos e, int puntos, int nro) {
        try {
            SessionFactory sesion = NewHibernateUtil.getSessionFactory();
            Session session;
            session=sesion.openSession();
            session.beginTransaction();
            
            Rondas ronda= new Rondas(p,e,new BigDecimal(nro),new BigDecimal(puntos));
            
            session.save(ronda);
            
            session.getTransaction().commit();
            session.close();
        }catch(HibernateException ex) {
            System.out.println("Algo salio mal");
        }
    }
    
    public List<Object[]> PartidasGanadas(){
        List<Object[]> lista = null;
        try {
            SessionFactory sesion = NewHibernateUtil.getSessionFactory();
            Session session;
            session=sesion.openSession();
            session.beginTransaction();
            System.out.println("22222");
            String hql = "select j.nombre, "
                    + "(select count(*) FROM Partidas where j.nombre=equipos.jugadoresByJugadoresId.nombre)+ "
                    + "(select count(*) FROM Partidas where j.nombre=equipos.jugadoresByJugadoresId1.nombre) "
                    + "FROM Jugadores j ";
            
            Query query = session.createQuery(hql);
            System.out.println("Aqiiiii");
            lista = query.list();
            System.out.println(lista);
            
            for (Object[] datos : lista) {
             System.out.println(datos[0] + "--" + datos[1]);
            }
            
            return lista;
        }
        catch(Exception e) {
            
        }
        return null;
    }

    public List<Object[]> PartidasEnCero(){
    List<Object[]> lista = null;
    try {
        SessionFactory sesion = NewHibernateUtil.getSessionFactory();
        Session session;
        session=sesion.openSession();
        session.beginTransaction();
        System.out.println("---- Partidas en 0 ----");
        
        String hql = "select j.nombre, count(j.nombre) "
                + "from PartidosEquipos  pe, Jugadores j "
                + "where "
                + "((pe.equipos.jugadoresByJugadoresId.id=j.id)"
                + "or (COALESCE(pe.equipos.jugadoresByJugadoresId1.id,-1)=j.id))"
                + "and (select sum(puntos) from Rondas "
                + "where (equipos.nombre=pe.equipos.nombre and partidas.id=pe.partidas.id)) is null "
                + "group by j.nombre";
        
        Query query = session.createQuery(hql); 
        
        lista = query.list();
        //System.out.println(lista);

        for (Object[] datos : lista) {
            System.out.println(datos[0] + "--" + datos[1]);
        }

            return lista;
        }
        catch(Exception e) {

        }
        return null;
    }
    
    public List<Object[]> PartidasJugador(){
       
        try {
            SessionFactory sesion = NewHibernateUtil.getSessionFactory();
            Session session;
            session=sesion.openSession();
            session.beginTransaction();
            Query partidas_jugador = session.createQuery(
                "SELECT j.nombre, (SELECT COUNT(*) "+
                                  "FROM Equipos e, PartidosEquipos pe "+
                                  "WHERE e.jugadoresByJugadoresId.nombre=j.nombre AND e.jugadoresByJugadoresId1.id IS NULL AND e.id=pe.equipos.id), "+
                                  "(SELECT COUNT(*) "+
                                  "FROM Equipos ee, PartidosEquipos pp "+
                                  "WHERE ((ee.jugadoresByJugadoresId.nombre=j.nombre AND ee.jugadoresByJugadoresId1.id IS NOT NULL) OR COALESCE(ee.jugadoresByJugadoresId1.nombre,'null')=j.nombre) AND ee.id=pp.equipos.id) "+
                "FROM Jugadores j"
            );
            List<Object[]> partidas = partidas_jugador.list();
            for (Object[] aRow : partidas) {
                //System.out.println(aRow[0] + " " + aRow[1] + " " + aRow[2]);
                //System.out.println(aRow[0] + " " + aRow[1] + " " + aRow[2] + " " + aRow[3] + " " + aRow[4]);
                System.out.println(aRow[0] + " " + aRow[1] + " " + aRow[2]);
            }
            
            return partidas;
        }
        catch(Exception e) {
            System.out.println(e);
        }
        
        return null;
    }
    
    public ArrayList<Equipos> PuntosRonda(){
        ArrayList<Equipos> lista = null;
        try {
            SessionFactory sesion = NewHibernateUtil.getSessionFactory();
            Session session;
            session=sesion.openSession();
            session.beginTransaction();
            System.out.println("Puntos Ronda");
            String hql = "select r.equipos "
                    + "from Rondas r "
                    + "where r.puntos in (select max(puntos) from Rondas) ";
            
            Query query = session.createQuery(hql);
            System.out.println("Aqiiiii");
            lista = (ArrayList<Equipos>)query.list();
            
            for(Equipos equipo : lista) {
                System.out.println(equipo.getNombre());
            }
            
            return lista;
        }
        catch(Exception e) {
            
        }
        return null;
    }
    
    public List<Object[]> MaxPuntosRonda(){
        List<Object[]> lista = null;
   
        try {
            SessionFactory sesion = NewHibernateUtil.getSessionFactory();
            Session session;
            session=sesion.openSession();
            session.beginTransaction();
            System.out.println("Puntos Ronda");
            String hql =  "select max(puntos) as puntos from Rondas";
            
            Query query = session.createQuery(hql);
            lista = query.list();
           
            return lista;
        }
        catch(Exception e) {
            
        }
        return null;
    }
    
    public Partidas ConsultaCreativa(){
        Partidas partida=null;
        try {
            SessionFactory sesion = NewHibernateUtil.getSessionFactory();
            Session session;
            session=sesion.openSession();
            session.beginTransaction();
            System.out.println("Puntos Ronda");
            String hql = "SELECT p from Partidas p join p.rondases r "
                    + "where r.numeroronda=(select max(ron.numeroronda) from Rondas ron) ";
            
            Query query = session.createQuery(hql);
            partida= (Partidas) query.uniqueResult();
            for(Equipos e : partida.getEquiposes()){
                System.out.println(e.getNombre());
            }   
            
            return partida;
        }
        catch(Exception e) {
           return null; 
        }
        
    }
    
    public List<Object[]> VictoriasJugador(){
        try {

            SessionFactory sesion = NewHibernateUtil.getSessionFactory();
            Session session;
            session=sesion.openSession();
            session.beginTransaction();
            /*Query cant_victorias = session.createQuery(
                "SELECT j.nombre, (SELECT COUNT(r.numeroronda) "+
                                  "FROM Equipos e, Rondas r "+
                                  "WHERE e.id=r.equipos.id AND "+
                                  "(e.jugadoresByJugadoresId.nombre=j.nombre OR e.jugadoresByJugadoresId1.nombre=j.nombre))+ "+
                                  "(SELECT COUNT(r.numeroronda) "+
                                  "FROM Equipos e, Rondas r "+
                                  "WHERE e.id=r.equipos.id AND "+
                                  "(e.jugadoresByJugadoresId.nombre=j.nombre AND e.jugadoresByJugadoresId1.id IS NULL)) "+
                "FROM Jugadores j "+
                "ORDER BY j.nombre"
            );*/
            Query jugadores = session.createQuery(
                "SELECT DISTINCT j.nombre, j.id "+
                "FROM Equipos ee "+
                "INNER JOIN ee.jugadoresByJugadoresId j "+
                "ORDER BY j.nombre"
            );
            
            Query cant_victorias = session.createQuery(
                "SELECT DISTINCT j.nombre, pp.id, (SELECT COUNT(r.numeroronda) "+
                                        "FROM Equipos e, Rondas r "+
                                        "WHERE e.id=r.equipos.id AND "+
                                        "(e.jugadoresByJugadoresId.nombre=j.nombre OR e.jugadoresByJugadoresId1.nombre=j.nombre))+ "+
                                        "(SELECT COUNT(r.numeroronda) "+
                                        "FROM Equipos e, Rondas r "+
                                        "WHERE e.id=r.equipos.id AND "+
                                        "(e.jugadoresByJugadoresId.nombre=j.nombre AND e.jugadoresByJugadoresId1.id IS NULL)) "+
                "FROM Equipos ee "+
                "INNER JOIN ee.jugadoresByJugadoresId j "+
                "INNER JOIN ee.rondases r "+
                "INNER JOIN r.partidas pp "+
                "ORDER BY j.nombre"
            );
          
            Query rondas = session.createQuery(
                "SELECT p.id, COUNT(p.id) "+
                "FROM Equipos e "+
                "INNER JOIN e.rondases r "+
                "INNER JOIN r.partidas p "+
                "GROUP BY p.id "
            );
            
            List<Object[]> partidas = cant_victorias.list();
            List<Object[]> ron = rondas.list();
            List<Object[]> jug = jugadores.list();
           
            for (Object[] aRow : partidas) {
                System.out.println(aRow[0] + " " + aRow[1] + " " + aRow[2]);
            }
            System.out.println("");
           
            for (Object[] aRow : ron) {
                System.out.println(aRow[0] + " " + aRow[1]);
            }
            System.out.println("");
            for (Object[] aRow : jug) {
                System.out.println(aRow[0]);
            }
            System.out.println("");
            float porc = 0;
            int acum = 0;
            int partida = 0;
            int victorias = 0;
            BigDecimal result = new BigDecimal(0);
           
            for (Object[] j : jug) {
                acum=0;
                result = new BigDecimal(0);
                System.out.println("---------------------------");
                System.out.println(j[0]);
                for (Object[] vic : partidas) {
                    if(j[0].equals(vic[0])){
                        System.out.println(j[0] +" "+vic[0]);
                        for (Object[] part : ron) {
                            if(part[0].equals(vic[1])){
                                System.out.println(part[0] + " " + vic[1]);
                                //partida = Integer.parseInt(String.valueOf(part[1]));
                                partida = Integer.valueOf(Integer.parseInt(String.valueOf(part[1]), 10));
                                System.out.println("Total: " +partida);
                                acum = acum + partida;
                            }
                        }
                        //victorias = Integer.parseInt(String.valueOf(vic[2]));
                        victorias = Integer.valueOf(Integer.parseInt(String.valueOf(vic[2]), 10));
                        System.out.println("Victorias: "+victorias);
                    }
                }
                if(acum!=0){
                    porc = (victorias/(float)acum)*100;
                    result=round(porc,2);
                }
                System.out.println(j[0] + " " +result);
            }
            return partidas;
        }
        catch(Exception e) {
            System.out.println(e);
        }
        
        return null;
    }
    
    public static BigDecimal round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);       
        return bd;
    }
}

