package by.baby.blogwebsite.service;

import by.baby.blogwebsite.dto.NewLikeDto;
import by.baby.blogwebsite.mapper.LikeDtoMapper;
import by.baby.blogwebsite.repository.LikeRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LikeService {

    private final LikeDtoMapper likeDtoMapper;
    private final LikeRepository likeRepository;

    public LikeService(LikeDtoMapper likeDtoMapper, LikeRepository likeRepository) {
        this.likeDtoMapper = likeDtoMapper;
        this.likeRepository = likeRepository;
    }

    public void saveLike(NewLikeDto newLikeDto) {
        Optional.of(newLikeDto)
                .map(likeDtoMapper::mapToEntity)
                .map(likeEntity -> {
                    likeEntity.getUser().getLikes().add(likeEntity);
                    likeEntity.getBlog().getLikes().add(likeEntity);
                    return likeEntity;
                })
                .map(likeRepository::save);
    }

    public void deleteLike(NewLikeDto newLikeDto) {
        likeRepository.deleteById(Optional.of(newLikeDto)
                .map(dto -> likeRepository.findByBlogIdAndUserId(newLikeDto.getBlogId(), dto.getUserId()).orElseThrow())
                .orElseThrow().getId());
    }

}
