package com.example.demo;
import org.springframework.beans.factory.annotation.Autowired;

@slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JdbcConfig.class})
public class TestSpringBean {

    @Autowired
    private org.springframework.jdbc.core.JdbcTemplate jdbcTemplate;
    @Autowired
    private DataSource dataSource;

    @Test
    public void test1() throws SQLException {
        System.out.println(jdbcTemplate.getDataSource() == dataSource); //true
        System.out.println(DataSourceUtils.getConnection(jdbcTemplate.getDataSource())); //com.mysql.jdbc.JDBC4Connection@17503f6b
        
        DynamicDataSourceContextHolder.setDataSourceId(DynamicDataSourceId.SLAVE1); 
        
        System.out.println(jdbcTemplate.getDataSource() == dataSource); //true
        System.out.println(DataSourceUtils.getConnection(jdbcTemplate.getDataSource())); //com.mysql.jdbc.JDBC4Connection@20bd8be5


        // 完成操作后  最好把数据源再set回去  否则可能会对该线程后续再使用JdbcTemplate的时候造成影响
        //DynamicDataSourceContextHolder.setDataSourceId(DynamicDataSourceId.MASTER);
    }
    
    @Test
    void addUser() {
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setUsername("user_" + i);
            user.setAge(random.nextInt(100));
            userMapper.insert(user);
        }
    }

    @Test
    void selectUser() {
        User user = userMapper.selectById(1268079294531719169L);
        System.out.println(user);
    }

	public static void main(String[] args) {
		package com.example.demo;
		
	}
}
