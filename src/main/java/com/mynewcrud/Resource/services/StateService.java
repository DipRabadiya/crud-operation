package com.mynewcrud.Resource.services;


import com.mynewcrud.Resource.entities.State;
import com.mynewcrud.Resource.pages.PageRequest;
import com.mynewcrud.Resource.repositories.StateRepository;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.net.URI;

@ApplicationScoped
public class StateService {

	@Inject
	StateRepository stateRepository;

	public Long count() {
		if(stateRepository.count() == 0)
			throw new WebApplicationException("States not found!", Response.Status.NOT_FOUND);
		
		return stateRepository.count();
	}
	
	public Response getAllPaged(PageRequest pageRequest) {
		if(stateRepository.findAll().count() == 0)
			throw new WebApplicationException("States not found!", Response.Status.NOT_FOUND);
		
		return Response
				.ok(stateRepository.findAll().page(Page.of(pageRequest.getPageNum(), pageRequest.getPageSize())).list())
				.build();
	}

	public Response persist(State state, UriInfo uriInfo) {
		stateRepository.persist(state);
		
		URI uri = uriInfo.getAbsolutePathBuilder().path("{id}").resolveTemplate("id", state.getId()).build();
		return Response.created(uri).build();
	}

	public Response update(Long id, State state) {
		State updateState = stateRepository.findById(id);

		if (stateRepository.findById(id) == null)
			throw new WebApplicationException("State not found!", Response.Status.NOT_FOUND);

		updateState.setName(state.getName());
		updateState.setRegion(state.getRegion());

		return Response.ok(updateState).build();
	}

	public Response delete(Long id) {
		if (stateRepository.findById(id) == null)
			throw new WebApplicationException("State not found!", Response.Status.NOT_FOUND);

		stateRepository.deleteById(id);
		return Response.noContent().build();
	}
}