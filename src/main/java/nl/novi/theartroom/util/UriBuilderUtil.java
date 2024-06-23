package nl.novi.theartroom.util;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

public class UriBuilderUtil{

    private UriBuilderUtil() {
    }

    public static URI buildUriBasedOnLongId(Long id, String path) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .path(path)
                .buildAndExpand(id)
                .toUri();
    }

    public static URI buildUriBasedOnStringId(String id, String path) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .path(path)
                .buildAndExpand(id)
                .toUri();
    }
}
