package it.cnr.missioni.rest.api.binder;

public interface IBinder<TO extends Object, FROM extends Object, B> {

    B withFrom(FROM from);

    TO bind();

    abstract class AbstractBinder<TO extends Object, FROM extends Object, B extends IBinder> implements IBinder<TO, FROM, B> {

        protected FROM from;

        /**
         *
         */
        protected AbstractBinder() {
        }

        /**
         * @param from
         */
        @Override
        public B withFrom(FROM from) {
            this.from = from;
            return self();
        }

        protected abstract B self();
    }
}
