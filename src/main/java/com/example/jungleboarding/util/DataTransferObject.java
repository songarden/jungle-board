package com.example.jungleboarding.util;

import java.security.NoSuchAlgorithmException;

public interface DataTransferObject<E> {
    E toEntity() throws NoSuchAlgorithmException;

    DataTransferObject<E> toDto(E entity);
}
