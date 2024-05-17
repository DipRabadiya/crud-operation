package com.mynewcrud.Resource.services;


import com.mynewcrud.Resource.entities.City;
import com.mynewcrud.Resource.entities.State;
import com.mynewcrud.Resource.pages.PageRequest;
import com.mynewcrud.Resource.repositories.CityRepository;
import com.mynewcrud.Resource.repositories.StateRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
public class CityService {

	@Inject
	StateRepository stateRepository;

	@Inject
	CityRepository cityRepository;

	private static final Logger logger = Logger.getLogger(CityService.class.getName());


	public long count() {
		logger.info("Getting count of cities.");
		long count = cityRepository.count();
		if (count == 0) {
			logger.log(Level.WARNING, "No Cities found.");
			throw new WebApplicationException("Cities not found!", Response.Status.NOT_FOUND);
		}
		logger.log(Level.INFO, "Number of Cities found: " + count);
		return cityRepository.count();
	}

	public Response getAllPaged(PageRequest pageRequest) {
		logger.info("Getting all cities paged.");
		if (cityRepository.findAll().count() == 0) {
			logger.log(Level.WARNING, "No Cities found.");
			throw new WebApplicationException("Cities not found!", Response.Status.NOT_FOUND);
		}
		logger.log(Level.INFO, "Cities retrieved successfully.");
		return Response
				.ok(cityRepository.findAll().page(Page.of(pageRequest.getPageNum(), pageRequest.getPageSize())).list())
				.build();
	}

	public Response getAllByStateId(Long id, PageRequest pageRequest) {
		logger.info("Getting all cities by state ID: " + id);
		if (cityRepository.find("state.id", id).count() == 0) {
			logger.log(Level.WARNING, "No State found.");
			throw new WebApplicationException("State not found!", Response.Status.NOT_FOUND);
		}
		logger.log(Level.INFO, "City retrieved successfully.");
		return Response.ok(cityRepository.find("state.id", id)
				.page(Page.of(pageRequest.getPageNum(), pageRequest.getPageSize())).list()).build();
	}

	public Response getAllByCityName(String name, PageRequest pageRequest) {
		logger.info("Getting all cities by name: " + name);
		if (cityRepository.find("name", name).count() == 0)
			throw new WebApplicationException("Name not found!", Response.Status.NOT_FOUND);

		PanacheQuery<City> city = cityRepository.find("name", name);
		return Response.ok(city.page(Page.of(pageRequest.getPageNum(), pageRequest.getPageSize())).list()).build();
	}

	public Response persist(City city) {
		logger.info("Persisting city: " + city.getName());
		State state = stateRepository.findById(city.getState().getId());
		if (state == null) {
			logger.log(Level.WARNING, "State with ID " + city.getState().getId() + " not found.");
			throw new WebApplicationException("State not found!", Response.Status.NOT_FOUND);
		}

		city.getState().setName(state.getName());
		city.getState().setRegion(state.getRegion());

		cityRepository.persist(city);
		logger.log(Level.INFO, "City added successfully.");
		return Response.ok(city).status(Response.Status.CREATED).build();
	}

	public Response update(Long id, City city) {
		logger.info("Updating city with ID: " + id);
		City updateCity = cityRepository.findById(id);

		if (updateCity == null) {
			logger.log(Level.WARNING, "City with ID " + id + " not found.");
			throw new WebApplicationException("City not found!", Response.Status.NOT_FOUND);
		}
		State state = stateRepository.findById(city.getState().getId());

		updateCity.setName(city.getName());
		updateCity.getState().setName(state.getName());
		updateCity.getState().setRegion(state.getRegion());
		logger.log(Level.INFO, "City updated successfully.");
		return Response.ok(updateCity).build();
	}

	public Response delete(Long id) {
		logger.info("Deleting city with ID: " + id);
		City city = cityRepository.findById(id);
		if (city == null) {
			logger.log(Level.WARNING, "City with ID " + id + " not found.");
			throw new WebApplicationException("City not found!", Response.Status.NOT_FOUND);
		}
		cityRepository.deleteById(id);
		logger.log(Level.INFO, "City deleted successfully.");
		return Response.noContent().build();
	}
}