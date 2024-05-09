package com.mynewcrud.Resource.repositories;


import com.mynewcrud.Resource.entities.State;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class StateRepository implements PanacheRepository<State> {
	
}
