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

@ApplicationScoped
public class CityService {

	@Inject
	StateRepository stateRepository;

	@Inject
	CityRepository cityRepository;

	public long count() {
		if (cityRepository.count() == 0)
			throw new WebApplicationException("Cities not found!", Response.Status.NOT_FOUND);

		return cityRepository.count();
	}

	public Response getAllPaged(PageRequest pageRequest) {
		if (cityRepository.findAll().count() == 0)
			throw new WebApplicationException("Cities not found!", Response.Status.NOT_FOUND);

		return Response
				.ok(cityRepository.findAll().page(Page.of(pageRequest.getPageNum(), pageRequest.getPageSize())).list())
				.build();
	}

	public Response getAllByStateId(Long id, PageRequest pageRequest) {
		if (cityRepository.find("state.id", id).count() == 0)
			throw new WebApplicationException("State not found!", Response.Status.NOT_FOUND);

		return Response.ok(cityRepository.find("state.id", id)
				.page(Page.of(pageRequest.getPageNum(), pageRequest.getPageSize())).list()).build();
	}

	public Response getAllByCityName(String name, PageRequest pageRequest) {
		if (cityRepository.find("name", name).count() == 0)
			throw new WebApplicationException("Name not found!", Response.Status.NOT_FOUND);

		PanacheQuery<City> city = cityRepository.find("name", name);
		// city.stream().forEach(x -> x.setName(x.getName().substring(3)));

		return Response.ok(city.page(Page.of(pageRequest.getPageNum(), pageRequest.getPageSize())).list()).build();
	}

	public Response persist(City city) {
		if (stateRepository.findById(city.getState().getId()) == null)
			throw new WebApplicationException("State not found!", Response.Status.NOT_FOUND);

		State state = stateRepository.findById(city.getState().getId());

		city.getState().setName(state.getName());
		city.getState().setRegion(state.getRegion());

		cityRepository.persist(city);
		return Response.ok(city).status(Response.Status.CREATED).build();
	}

	public Response update(Long id, City city) {
		City updateCity = cityRepository.findById(id);

		if (cityRepository.findById(id) == null)
			throw new WebApplicationException("City not found!", Response.Status.NOT_FOUND);

		State state = stateRepository.findById(city.getState().getId());

		updateCity.setName(city.getName());
		updateCity.getState().setName(state.getName());
		updateCity.getState().setRegion(state.getRegion());

		return Response.ok(updateCity).build();
	}

	public Response delete(Long id) {
		if (cityRepository.findById(id) == null)
			throw new WebApplicationException("City not found!", Response.Status.NOT_FOUND);

		cityRepository.deleteById(id);
		return Response.noContent().build();
	}
}