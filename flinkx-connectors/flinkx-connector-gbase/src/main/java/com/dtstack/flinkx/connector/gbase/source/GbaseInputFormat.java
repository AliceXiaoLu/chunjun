/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dtstack.flinkx.connector.gbase.source;

import org.apache.flink.core.io.InputSplit;
import org.apache.flink.table.types.logical.LogicalType;
import org.apache.flink.table.types.logical.RowType;

import com.dtstack.flinkx.connector.gbase.converter.GbaseRawTypeConverter;
import com.dtstack.flinkx.connector.jdbc.source.JdbcInputFormat;
import com.dtstack.flinkx.util.TableUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

/**
 * @author tiezhu
 * @since 2021/5/10 5:22 下午
 */
public class GbaseInputFormat extends JdbcInputFormat {

    private static final Logger LOG = LoggerFactory.getLogger(GbaseInputFormat.class);

    @Override
    public void openInternal(InputSplit inputSplit) {
        super.openInternal(inputSplit);
        try {
            LogicalType rowType =
                    TableUtil.createRowType(column, columnType, GbaseRawTypeConverter::apply);
            setRowConverter(jdbcDialect.getColumnConverter((RowType) rowType));
        } catch (SQLException e) {
            LOG.error("", e);
        }
    }
}
