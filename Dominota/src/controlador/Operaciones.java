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
import java.util.Set;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import modelo.*;
import org.hibernate.HibernateException;

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
                    "SELECT e.id, SUM(r.puntos),p.id from Rondas r join r.equipos e join r.partidas p where r.partidas.id=? and r.equipos.id=? group by r.equipos.id, r.partidas.id");
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
}
