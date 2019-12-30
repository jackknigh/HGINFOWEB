package com.utils.sys;

import com.spinfosec.system.RspCode;
import com.spinfosec.system.TMCException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

public class ValidatorUtils {
        private static final Logger log = LoggerFactory.getLogger(ValidatorUtils.class);
        private static Validator validator;

        static {
            validator = Validation.buildDefaultValidatorFactory().getValidator();
        }

        /**
         * 校验对象
         *
         * @param object 待校验对象
         * @param groups 待校验的组
         * @throws Exception 校验不通过，则报RRException异常
         */
        public static void validateEntity(Object object, Class<?>... groups)
                throws RuntimeException {
            Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, groups);
            if (!constraintViolations.isEmpty()) {
                StringBuilder msg = new StringBuilder();
                for (ConstraintViolation<Object> constraint : constraintViolations) {
                    msg.append(constraint.getMessage());
                    log.error("入参错误：{}", msg.toString());
                    RspCode errCode = RspCode.FAILURE;
                    errCode.setDescription(errCode, msg.toString());
                    throw new TMCException(errCode);
                }
            }
        }
}
