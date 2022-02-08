package com.rekkme.util;

import com.rekkme.data.entity.Comment;
import com.rekkme.data.entity.Rek;
import com.rekkme.data.entity.RekResult;
import com.rekkme.data.entity.User;
import com.rekkme.data.repository.RekRepository;
import com.rekkme.data.repository.UserRepository;
import com.rekkme.dtos.entity.CommentDto;
import com.rekkme.dtos.entity.RekDto;
import com.rekkme.dtos.entity.RekResultDto;
import com.rekkme.dtos.entity.UserDto;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ConverterUtil {

    private final ModelMapper modelMapper;
    private final RekRepository rekRepository;
    private final UserRepository userRepository;
    
    public UserDto convertToUserDto(User user) {
        UserDto userDto = this.modelMapper.map(user, UserDto.class);
        userDto.setNumFriends(this.userRepository.findNumFriends(user.getUserId()));
        userDto.setNumFollowing(this.userRepository.findNumFollowing(user.getUserId()));
        userDto.setNumFollowers(this.userRepository.findNumFollowers(user.getUserId()));
        return userDto;
    }

    public RekDto convertRekToDto(Rek rek, User user) {
        RekDto rekDto = this.modelMapper.map(rek, RekDto.class);
        if (user == null) {
            return rekDto;
        }
        if (this.rekRepository.existsLike(user.getUserId(), rek.getRekId()) > 0) {
            rekDto.setLiked(true);
        }
        rekDto.setNumLikes(this.rekRepository.getNumLikes(rek.getRekId()));
        return rekDto;
    }

    public RekResultDto convertRekResultToDto(RekResult rek) {
        RekResultDto rekDto = this.modelMapper.map(rek, RekResultDto.class);
        return rekDto;
    }

    public CommentDto convertCommentToDto(Comment comment) {
        CommentDto commentDto = this.modelMapper.map(comment, CommentDto.class);
        return commentDto;
    }
    
}
