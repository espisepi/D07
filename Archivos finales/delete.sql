start transaction;

use `Acme-Explorer`;

revoke all privileges on `Acme-Explorer`.* from 'acme-user'@'%';
revoke all privileges on `Acme-Explorer`.* from 'acme-manager'@'%';

drop user 'acme-user'@'%';
drop user 'acme-manager'@'%';

drop database `Acme-Explorer`;

commit;