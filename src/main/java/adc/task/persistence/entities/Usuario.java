package adc.task.persistence.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity(name="usuario")
public class Usuario {//implements UserDetails{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String username;
	private String password;
	private String nombre;
	
	@OneToMany(mappedBy = "usuario", 
           fetch = FetchType.LAZY)
	private List<Tarea> tareas = new ArrayList<>();
	
	@OneToMany(mappedBy = "usuario", 
			fetch = FetchType.LAZY)
	private List<Token> tokens = new ArrayList<>();
	
	public Usuario(){
		   super();
		}
	
	public Usuario(int id, String username, String password, String nombre) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.nombre = nombre;
	}
	
	public Usuario(String username, String password, String nombre) {
		super();
		this.username = username;
		this.password = password;
		this.nombre = nombre;
		this.tokens = tokens;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String passwd) {
		this.password = passwd;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/*@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return List.of();
	}*/

}
