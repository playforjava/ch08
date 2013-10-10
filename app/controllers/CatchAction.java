package controllers;

import play.Logger;
import play.libs.F;
import play.mvc.*;
import utils.ExceptionMailer;

public class CatchAction extends Action.Simple {

    public F.Promise<SimpleResult> call(Http.Context ctx) {
        try {
            return delegate.call(ctx);
        } catch (Throwable e) {
            ExceptionMailer.send(e);
            throw new RuntimeException(e);
        }
    }

}