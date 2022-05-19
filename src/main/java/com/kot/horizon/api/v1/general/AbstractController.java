package com.kot.horizon.api.v1.general;

import java.util.Optional;
import javax.validation.Valid;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.kot.horizon.api.v1.ResponsePage;
import com.kot.horizon.architecture.model.BaseEntity;
import com.kot.horizon.architecture.service.AbstractService;

@Validated
public abstract class AbstractController<
				Entity extends BaseEntity,
				RequestBean extends AbstractRequest, 
				ResponseBean extends AbstractResponse, 
				Service extends AbstractService<Entity>,
				APIService extends AbstractAPIService<Entity, RequestBean, ResponseBean, Service>> {

	@Autowired protected APIService apiService;

	@ApiOperation(value = "Create item")
	@ResponseStatus( HttpStatus.CREATED )
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseBean create( @RequestBody @Valid RequestBean request ) {
		return apiService.create(request);
	}
	
	@ApiOperation(value = "Retrieve a list of all items")
	@GetMapping( produces = MediaType.APPLICATION_JSON_VALUE )
	public ResponsePage< ResponseBean > findItems(
			@ApiParam( "Search expression used to filter results. To check fields which support filtering, " +
					"please refer to response model" )
			@RequestParam(required = false) Optional<String> search,
			@ApiParam( "Page number")
			@RequestParam(required = false) Optional<Integer> pageNo,
			@ApiParam( "The size of the pager")
			@RequestParam(required = false) Optional<Integer> pageSize,
			@ApiParam( "Specify sort fields") Sort sort,
			@ApiParam ("specify field to be expanded")
			@RequestParam(required = false) Optional<String> expand) {
		return apiService.findItems(search, pageNo, pageSize, sort, expand);
	}

	@ApiOperation(value = "Retrieve a specific item by ID")
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseBean findById( @PathVariable Long id,
								  @ApiParam ("specify field to be expanded")
								  @RequestParam(required = false) Optional<String> expand) {
		return apiService.findById(id, expand);
	}

	@ApiOperation(value = "Update a specific item, based on the ID")
	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseBean update( @PathVariable Long id, @RequestBody @Valid RequestBean request ) {
		return apiService.update(id, request);
	}
	
	@ApiOperation(value = "Patch a specific item, based on the ID")
	@PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void patch( @PathVariable Long id, @RequestBody RequestBean request ){
		apiService.patch( id, request );
	}

	@ApiOperation(value = "Delete item by ID")
	@ResponseStatus( HttpStatus.NO_CONTENT ) 
	@DeleteMapping(value = "/{id}")
	public void delete( @PathVariable Long id ) {
		apiService.delete(id);
	}
}
