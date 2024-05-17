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
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
public class StateService {

	@Inject
	StateRepository stateRepository;

	private static final Logger logger = Logger.getLogger(StateService.class.getName());


	public Long count() {
		logger.info("Getting count of states.");
		Long count = stateRepository.count();
		if(count == 0) {
			logger.log(Level.WARNING, "No States found.");
			throw new WebApplicationException("States not found!", Response.Status.NOT_FOUND);
		}
		logger.log(Level.INFO, "Number of states found: " + count);
		return stateRepository.count();
	}

	public Response getAllPaged(PageRequest pageRequest) {
		logger.info("Getting all states paged.");
		Long stateCount = stateRepository.findAll().count();
		if(stateCount== 0) {
			logger.log(Level.WARNING, "No states found for the given page request.");
			throw new WebApplicationException("States not found!", Response.Status.NOT_FOUND);
		}
		logger.log(Level.INFO, "States retrieved successfully.");
		return Response
				.ok(stateRepository.findAll().page(Page.of(pageRequest.getPageNum(), pageRequest.getPageSize())).list())
				.build();
	}

	public Response persist(State state) {
		logger.info("Persisting state: " + state.getName());
		stateRepository.persist(state);
		logger.log(Level.INFO, "Publishing house added successfully.");
		return Response.ok(state).build();
	}

	public Response update(Long id, State state) {
		logger.info("Updating state with ID: " + id);
		State updateState = stateRepository.findById(id);

		if (updateState == null) {
			logger.log(Level.WARNING, "State with " + id + " not found.");
			throw new WebApplicationException("State not found!", Response.Status.NOT_FOUND);
		}
		updateState.setName(state.getName());
		updateState.setRegion(state.getRegion());
		logger.log(Level.INFO, "State updated successfully.");
		return Response.ok(updateState).build();
	}

	public Response delete(Long id) {
		logger.info("Deleting state with ID: " + id);
		if (stateRepository.findById(id) == null) {
			logger.log(Level.WARNING, "State with ID " + id + " not found.");
			throw new WebApplicationException("State not found!", Response.Status.NOT_FOUND);
		}
		stateRepository.deleteById(id);
		logger.log(Level.INFO, "State deleted successfully.");
		return Response.noContent().build();
	}
}