package com.agileboot.domain.view;

import com.agileboot.orm.view.service.IViewMovieService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RealTimeAppService {

    @NonNull
    private IViewMovieService iViewMovieService;



}
