package com.courage.library.service.command;

import com.courage.library.model.User;
import com.courage.library.model.dto.PasswordDTO;

public interface AccountCommand {

	User updatePassword(PasswordDTO passwordDTO);
//	User confirmAccount(String token);
//	User resetPassword(String email);

}
