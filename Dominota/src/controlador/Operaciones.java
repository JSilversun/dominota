/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import modelo.*;

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
    public void crearPartidaEquipos(Equipos equipo1, Equipos equipo2, int puntos){
        try {
            SessionFactory sesion = NewHibernateUtil.getSessionFactory();
            Session session;
            session=sesion.openSession();
            session.beginTransaction();
            Partidas partida = new Partidas(new Date(),new BigDecimal(puntos));
            PartidosEquipos Partidoequipos1 = new PartidosEquipos(partida, equipo1);
            PartidosEquipos Partidoequipos2 = new PartidosEquipos(partida, equipo2);
            
            session.save(partida);
            session.save(Partidoequipos1);
            session.save(Partidoequipos2);
            session.getTransaction().commit();
            session.close();
            JOptionPane.showMessageDialog(null, "Partida creada con exito!");
        }
        catch(Exception e) {
            System.out.println("error");
        }
    }
    
    public void crearPartidaIndividuales(ArrayList<Equipos> equipos, int puntos){
        try {
            SessionFactory sesion = NewHibernateUtil.getSessionFactory();
            Session session;
            session=sesion.openSession();
            session.beginTransaction();
            Partidas partida = new Partidas(new Date(),new BigDecimal(puntos));
            session.save(partida);
            for (int i = 0; i < equipos.size(); i++) {
                PartidosEquipos Partidoequipos1 = new PartidosEquipos(partida, equipos.get(i));
                session.save(Partidoequipos1);
            }     
            session.getTransaction().commit();
            session.close();
            JOptionPane.showMessageDialog(null, "Partida creada con exito!");
        }
        catch(Exception e) {
            System.out.println("error");
        }
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
            return equipo;
        }
        catch(Exception e) {
            System.out.println("Algo salio mal");
            return null;
        }
        
    } 
    
    public List<Object[]> PartidasJugador(){
       
        try {
            SessionFactory sesion = NewHibernateUtil.getSessionFactory();
            Session session;
            session=sesion.openSession();
            session.beginTransaction();
            //select equipos.id, count(*) FROM Partidas group by equipos.id
            /*Query partidas_individuales = session.createQuery(
                "SELECT e.jugadoresByJugadoresId.nombre, COUNT(*), (SELECT COUNT(*) "+
                                                                   "FROM Equipos eq, PartidosEquipos pe, Jugadores j "+   
                                                                   "WHERE eq.jugadoresByJugadoresId1.id IS NOT NULL AND pe.equipos.id=eq.id AND (eq.jugadoresByJugadoresId=e.jugadoresByJugadoresId.id OR eq.jugadoresByJugadoresId1=e.jugadoresByJugadoresId.id) "+
                                                                   "GROUP BY e.jugadoresByJugadoresId.id, e.jugadoresByJugadoresId.nombre) "+
                "FROM Equipos e , PartidosEquipos pe "+
                "WHERE e.jugadoresByJugadoresId1.id IS NULL AND pe.equipos.id=e.id "+
                "GROUP BY e.jugadoresByJugadoresId.id, e.jugadoresByJugadoresId.nombre "+
                "ORDER BY e.jugadoresByJugadoresId.nombre ASC"
            );*/
            /*Query partidas_individuales = session.createQuery(
                "SELECT e.id, e.jugadoresByJugadoresId.nombre, jugadoresByJugadoresId1.id, (SELECT COUNT(*) "+
                                                                                            "FROM PartidosEquipos pe "+
                                                                                            "WHERE pe.equipos.id=e.id AND e.jugadoresByJugadoresId1.id IS NULL), "+
                                                                                            "(SELECT COUNT(*) "+
                                                                                            "FROM PartidosEquipos pp "+
                                                                                            "WHERE pp.equipos.id=e.id AND (e.jugadoresByJugadoresId.id=pp.equipos.jugadoresByJugadoresId.id OR e.jugadoresByJugadoresId1.id=pp.equipos.jugadoresByJugadoresId.id)) "+
                "FROM Equipos e"
            );*/
            Query partidas_jugador = session.createQuery(
                "SELECT j.nombre, (SELECT COUNT(*) "+
                                  "FROM Equipos e, PartidosEquipos pe "+
                                  "WHERE e.jugadoresByJugadoresId.nombre=j.nombre AND e.jugadoresByJugadoresId1.id IS NULL AND e.id=pe.equipos.id), "+
                                  "(SELECT COUNT(*) "+
                                  "FROM Equipos ee, PartidosEquipos pp "+
                                  "WHERE ((ee.jugadoresByJugadoresId.nombre=j.nombre AND ee.jugadoresByJugadoresId1.id IS NOT NULL) OR ee.jugadoresByJugadoresId1.nombre=j.nombre) AND ee.id=pp.equipos.id) "+
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
}
