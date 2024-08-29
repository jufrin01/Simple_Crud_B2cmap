package idb2camp.b2campjufrin.utils;


import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class CustomPage<T> extends PageImpl<T> {
    public CustomPage(List<T> content, Pageable pageable, long total) {

        super(content, pageable, total);
    }
}

