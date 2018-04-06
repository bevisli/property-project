package app.bo.service.auth.service;

import app.bo.service.user.domain.User;
import core.framework.crypto.Hash;
import core.framework.db.Repository;
import core.framework.inject.Inject;
import core.framework.web.exception.NotFoundException;
import core.framework.web.exception.UnauthorizedException;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author mort
 */
public class AuthorizationService {
    @Inject
    Repository<User> userRepository;

    public AuthorizationResponse authorize(AuthorizationRequest request) {
        Optional<User> userOptional = userRepository.selectOne("phone_number = ? AND password=? AND status='ACTIVE'", request.phoneNumber, Hash.md5Hex(request.password));
        if (!userOptional.isPresent()) {
            throw new UnauthorizedException("user and password not match", "Login failed, phone_number=" + request.phoneNumber);
        }
        AuthorizationResponse response = new AuthorizationResponse();
        response.user = new AuthorizationResponse.User();
        response.user.id = userOptional.get().id;
        response.user.phoneNumber = userOptional.get().phoneNumber;
        response.user.userName = userOptional.get().name;
        return response;
    }

    public void resetPassword(String id, ResetPasswordRequest request) {
        User user = userRepository.get(id).orElseThrow(() -> new NotFoundException("user not found, id=" + id));
        if (!user.password.equals(Hash.md5Hex(request.oldPassword))) {
            throw new UnauthorizedException("incorrect password");
        }
        user.updatedBy = request.requestedBy;
        user.updatedTime = LocalDateTime.now();
        user.password = Hash.md5Hex(request.password);
        userRepository.update(user);
    }
}
